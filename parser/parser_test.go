package parser

import (
	"bufio"
	"fmt"
	"os"
	"path/filepath"
	"strings"
	"testing"
)

const TestFloder = "../test"

func getJavaFiles() []string {
	var files []string

	javaSuffix := ".java"
	err := filepath.Walk(TestFloder, func(path string, info os.FileInfo, err error) error {
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
		sqls, err := GetSqlFromJavaFile(file)
		if err != nil {
			t.Error(err)
		}
		sqlFileSqls := getSqlsFromSqlFile(file + ".sql")
		for i, sql := range sqls {
			if sql != sqlFileSqls[i] {
				t.Error(fmt.Errorf("sql parser failed, java file: %s", file))
			}
		}
	}
}
