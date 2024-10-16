#define COL1_WIDTH 5
#define COL2_WIDTH 16
#define COL3_WIDTH 12

#include <iostream>
#include <codecvt>
#include <iomanip>
#include <sstream>

using namespace std;

void horizontal_line() {
    for (int i = 0; i < 2+COL1_WIDTH+COL2_WIDTH+COL3_WIDTH; i++) {
        cout << "-";
    }
    cout << "\n";
}

int str_len(string s) {
    wstring_convert<codecvt_utf8_utf16<wchar_t>> converter;
    wstring wide = converter.from_bytes(s);
    return wide.length();
}

void print_cell(string s, int width) {
    int w = width - str_len(s);
    cout << setw(w/2) << "" << s << setw(w/2 + w%1) << "";
}

void print_row(string c1, string c2, string c3) {
    cout << "|";
    print_cell(c1, COL1_WIDTH);
    cout << "|";
    print_cell(c2, COL2_WIDTH);
    cout << "|";
    print_cell(c3, COL3_WIDTH);
    cout << "|\n";
}

int main() {
    horizontal_line();
    print_row("№", "Выражение", "Результат");
    horizontal_line();
    stringstream ans;
    int x = 2;
    int y = 5;
    ans << 2/5;
    print_row(" 1.", "z=2/5", ans.str());
    ans.str("");
    ans << 2./5;
    print_row(" 2.", " z=2./5", ans.str());
    ans.str("");
    ans << 2/5.;
    print_row(" 3.", " z=2/5.", ans.str());
    ans.str("");
    ans << x/5;
    print_row(" 4.", "z=x/5", ans.str());
    ans.str("");
    ans << x/5.;
    print_row(" 5.", " z=x/5.", ans.str());
    ans.str("");
    ans << x/y;
    print_row(" 6.", "z=x/y", ans.str());
    ans.str("");
    ans << double(x)/y;
    print_row(" 7.", "z=double(x)/y", ans.str());
    ans.str("");
    ans << x/double(y);
    print_row(" 8.", "z=x/double(y)", ans.str());
    ans.str("");
    ans << 2%5;
    print_row(" 9.", "z=2%5", ans.str());
    ans.str("");
    ans << y%x;
    print_row("10.", "z=y%x", ans.str());
    horizontal_line();
    return 0;
}
