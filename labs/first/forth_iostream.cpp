#include <iomanip>
#include <iostream>
#include <string>

using namespace std;

int main() {
    string buf;
    cout << "Your name: ";
    cin >> buf;
    cout << setw(40) << "Hello, " + buf + "!" << "\n";
    return 0;
}
