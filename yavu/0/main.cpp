#include <iostream>
#include <sstream>
#include "box/Box.hpp"
#include "container/container.hpp"

// unity build
#include "box/Box.cpp"
#include "container/container.cpp"

int main() {
    Box b1(30, 40, 50, 12.5, 1500);
    Box b2(20, 25, 30, 8.2, 900);
    Box b3(10, 15, 20, 3.0, 450);
    Box b4(5, 5, 5, 1.1, 100);

    std::cout << "Boxes created:\n";
    std::cout << b1 << "\n" << b2 << "\n" << b3 << "\n" << b4 << "\n\n";

    Container cont(100, 100, 100, 30.0);
    std::cout << "Empty container:\n" << cont << "\n";

    try {
        int idx1 = cont.add_box(b1);
        int idx2 = cont.add_box(b2);
        int idx3 = cont.add_box(b3);
        std::cout << "Added boxes at indices: " << idx1 << ", " << idx2 << ", " << idx3 << "\n";
        int idx4 = cont.add_box(b4);
        std::cout << "Added box at index: " << idx4 << "\n";
    } catch (const DoesNotFitException&) {
        std::cout << "Could not add box: weight limit exceeded.\n";
    }

    std::cout << "\nContainer after adding all boxes:\n";
    std::cout << "Count: " << cont.count() << "\n";
    std::cout << "Total weight: " << cont.total_weight() << " kg\n";
    std::cout << "Total value: " << cont.total_value() / 100.0 << " rub\n";
    std::cout << "Boxes:\n";
    for (int i = 0; i < cont.count(); ++i) {
        std::cout << "  [" << i << "] " << cont[i] << "\n";
    }

    std::cout << "\nChanging the value of the second box...\n";
    cont[1].set_value(2000);
    std::cout << "New value of box[1]: " << cont[1].get_value() / 100.0 << " rub\n";

    std::cout << "\nRemoving the third box...\n";
    Box removed = cont.remove_box(2);
    std::cout << "Removed: " << removed << "\n";
    std::cout << "Now container has " << cont.count() << " boxes.\n";

    std::stringstream ss;
    ss << cont;
    Container cont2(0,0,0,0);
    ss >> cont2;
    std::cout << "\nContainer after serialisation/deserialisation:\n" << cont2;

    return 0;
}
