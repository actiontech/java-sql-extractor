package parser

import (
	"os"
	"testing"
)

// TestSimplePanicFix 简单测试修复是否有效
func TestSimplePanicFix(t *testing.T) {
	// 创建一个会导致panic的Expression
	expr := &Expression{
		Content:   "\"SELECT * FROM users\"",
		RuleIndex: 52, // JavaParserRULE_literal
		Symbol:    "+",
		Next:      nil, // 这里是nil，修复前会导致panic
	}

	// 测试修复后的函数不会panic
	defer func() {
		if r := recover(); r != nil {
			t.Errorf("函数仍然panic: %v", r)
		}
	}()

	// 调用修复后的函数
	result := getStrValueFromExpression(expr)

	// 验证函数正常返回（不panic）
	if result == nil {
		t.Error("结果不应该为nil")
	}

	// 验证返回了正确的字符串（因为Next为nil，应该返回原始字符串）
	if len(result) == 1 && result[0] != "SELECT * FROM users" {
		t.Errorf("期望: SELECT * FROM users, 实际: %s", result[0])
	}
}

// TestGetSqlFromJavaFile 测试GetSqlFromJavaFile方法的各种场景
func TestGetSqlFromJavaFile(t *testing.T) {
	testCases := []struct {
		name        string
		javaCode    string
		shouldPanic bool
	}{
		{
			name: "包含null拼接的表达式",
			javaCode: `
public class TestClass {
    public void testMethod() {
        String sql = "SELECT * FROM users" + null;
        executeQuery(sql);
    }
    
    private void executeQuery(String sql) {
        // JDBC调用
    }
}`,
			shouldPanic: false,
		},
		{
			name: "不完整的表达式_加号后换行",
			javaCode: `
public class TestClass {
    public void testMethod() {
        String sql = "SELECT * FROM users" + 
        executeQuery(sql);
    }
    
    private void executeQuery(String sql) {
        // JDBC调用
    }
}`,
			shouldPanic: false, // 修复后不应该panic
		},
		{
			name: "正常的多行表达式",
			javaCode: `
public class TestClass {
    public void testMethod() {
        String sql = "SELECT * FROM users" + 
                     " WHERE id = 1";
        executeQuery(sql);
    }
    
    private void executeQuery(String sql) {
        // JDBC调用
    }
}`,
			shouldPanic: false,
		},
		{
			name: "复杂的多行拼接",
			javaCode: `
public class TestClass {
    public void testMethod() {
        String sql = "SELECT " + 
                     "id, name, email " +
                     "FROM users " +
                     "WHERE status = 'active'";
        executeQuery(sql);
    }
    
    private void executeQuery(String sql) {
        // JDBC调用
    }
}`,
			shouldPanic: false,
		},
		{
			name: "语法错误的表达式",
			javaCode: `
public class TestClass {
    public void testMethod() {
        String sql = "SELECT * FROM users" + ; // 语法错误
        executeQuery(sql);
    }
    
    private void executeQuery(String sql) {
        // JDBC调用
    }
}`,
			shouldPanic: false, // 修复后不应该panic，即使语法错误
		},
	}

	for _, tc := range testCases {
		t.Run(tc.name, func(t *testing.T) {
			// 创建临时文件
			tmpFile, err := os.CreateTemp("", "test_*.java")
			if err != nil {
				t.Fatalf("创建临时文件失败: %v", err)
			}
			defer os.Remove(tmpFile.Name())

			// 写入Java代码
			if _, err := tmpFile.WriteString(tc.javaCode); err != nil {
				t.Fatalf("写入Java代码失败: %v", err)
			}
			tmpFile.Close()

			// 测试GetSqlFromJavaFile函数不会panic
			defer func() {
				if r := recover(); r != nil {
					if !tc.shouldPanic {
						t.Errorf("测试用例 %s 意外panic: %v", tc.name, r)
					}
				}
			}()

			// 调用函数
			sqls, err := GetSqlFromJavaFile(tmpFile.Name())
			if err != nil {
				t.Logf("解析错误（可能正常）: %v", err)
			}

			// 验证函数正常返回（不panic）
			if sqls == nil {
				t.Error("结果不应该为nil")
			}
		})
	}
}

// BenchmarkPanicFix 性能测试确保修复不影响性能
func BenchmarkPanicFix(b *testing.B) {
	expr := &Expression{
		Content:   "\"SELECT * FROM users\"",
		RuleIndex: 52,
		Symbol:    "+",
		Next:      nil,
	}

	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		getStrValueFromExpression(expr)
	}
}
