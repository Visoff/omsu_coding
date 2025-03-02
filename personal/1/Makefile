all: build_lib bin run clean

build_lib:
	gcc -fpic -shared -o lib/lib.so lib/lib.c -Ilib

bin:
	gcc -o main main.c lib/lib.so

run:
	./main

clean:
	rm main lib/lib.so
