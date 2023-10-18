package test

import (
	"testing"
	"os"
	"fmt"
	"bufio"
	"path/filepath"
	"strings"

	"github.com/actiontech/java-sql-extractor/parser"
)

func getJavaFiles() []string {
	var files []string

	testFolder := "."
	javaSuffix := ".java"
	err := filepath.Walk(testFolder, func(path string, info os.FileInfo, err error) error {
		if strings.HasSuffix(path, javaSuffix) {
			files = append(files, path)
		}
		return nil
	})
	if err != nil {
		panic(err)
	}

	return files
}

func getSqlsFromSqlFile(sqlFile string) []string {
	sqls := []string{}
	f, err := os.Open(sqlFile)
	if err != nil {
		return sqls
	}
	defer f.Close()
	scanner := bufio.NewScanner(f)

	for scanner.Scan() {
        sqls = append(sqls, scanner.Text())
    }

	return sqls
}

func TestJavaFile(t *testing.T) {
	javaFiles := getJavaFiles()
	for _, file := range javaFiles {
		javaParser, err := parser.CreateJavaParser(file)
		if err != nil {
			t.Error(err)
		}
		v := parser.NewJavaVisitor()
		javaParser.CompilationUnit().Accept(v)
		sqls := parser.GetSqlsFromVisitor(v)
		sqlFileSqls := getSqlsFromSqlFile(file+".sql")
		for i, sql := range sqls {
			if sql != sqlFileSqls[i] {
				t.Error(fmt.Errorf("sql parser failed, java file: %s", file))
			}
		}
	}
}
