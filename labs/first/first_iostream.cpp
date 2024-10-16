#include <iomanip>
#include <ios>
#include <iostream>

using namespace std;

int main() {
    float x = 829.356;
    float y = 128.286;
    cout << "1) " << x << " + " << y << " = " << x+y << "\n";
    cout << "2) " << scientific << setprecision(6) << x << "\n";
    cout << "3) " << scientific << setprecision(2) << y << "\n";
    cout << "4) " << fixed << setprecision(2) << x << "\n";
    cout << "5) " << fixed << setprecision(3) << setw(13) << y << "\n";
    return 0;
}
