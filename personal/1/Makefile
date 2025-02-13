all: build run clean

build:
	gcc -lstdc++ main.cpp -o main

run:
	./main

clean:
	rm main

build_test:
	gcc -lstdc++ main.test.cpp -o main

test: build_test run clean
