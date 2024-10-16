#include <iomanip>
#include <iostream>

using namespace std;

int main() {
    double x, y;
    cout << "x: ";
    cin >> x;
    cout << "y: ";
    cin >> y;
    int places, precision;
    cout << "places: ";
    cin >> places;
    cout << "precision: ";
    cin >> precision;
    cout << fixed << setw(places) << setprecision(precision) << x + y << "\n";
    cout << fixed << setw(places) << setprecision(precision) << x - y << "\n";
    cout << fixed << setw(places) << setprecision(precision) << x * y << "\n";
    cout << fixed << setw(places) << setprecision(precision) << x / y << "\n";
    return 0;
}
