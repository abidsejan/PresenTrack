#!/bin/bash

# Set variables
PROJECT_DIR="$(pwd)"
BIN_DIR="$PROJECT_DIR/bin"
LIB_DIR="$PROJECT_DIR/lib"
SRC_DIR="$PROJECT_DIR/src/main/java"

echo "Cleaning previous build..."
rm -rf "$BIN_DIR"

echo "Creating bin directory..."
mkdir -p "$BIN_DIR"

echo "Compiling all Java files..."
# Find all Java files and compile them
find "$SRC_DIR" -name "*.java" -type f -print0 | xargs -0 javac -d "$BIN_DIR" -cp "$LIB_DIR/*"

echo "Checking if Main.class was created..."
if [ -f "$BIN_DIR/com/attendance/Main.class" ]; then
    echo "Main.class found. Running application..."
    java -cp "$BIN_DIR:$LIB_DIR/*" com.attendance.Main
else
    echo "Error: Main.class not found!"
    echo "Contents of bin directory:"
    find "$BIN_DIR" -type f | head -10
    echo ""
    echo "Attempting to compile Main.java explicitly..."
    javac -d "$BIN_DIR" -cp "$LIB_DIR/*" "$SRC_DIR/com/attendance/Main.java"
    
    if [ -f "$BIN_DIR/com/attendance/Main.class" ]; then
        echo "Main.class created after explicit compilation. Running application..."
        java -cp "$BIN_DIR:$LIB_DIR/*" com.attendance.Main
    else
        echo "Error: Main.class still not found after explicit compilation!"
        echo "Checking for compilation errors:"
        javac -verbose -d "$BIN_DIR" -cp "$LIB_DIR/*" "$SRC_DIR/com/attendance/Main.java"
    fi
fi
