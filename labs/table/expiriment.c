#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <termios.h>
#include <unistd.h>

struct termios default_tty;

void disable_raw_terminal() { tcsetattr(STDIN_FILENO, TCSANOW, &default_tty); }

void enable_raw_terminal() {
  tcgetattr(STDIN_FILENO, &default_tty);
  atexit(disable_raw_terminal);

  struct termios tty;

  tcgetattr(STDIN_FILENO, &tty);

  tty.c_cc[VMIN] = 1;
  tty.c_cc[VTIME] = 1;

  tty.c_lflag &= ~(ICANON | ECHO | ISIG);

  tcsetattr(STDIN_FILENO, TCSANOW, &tty);
}

uint8_t get_byte_len(char *s) {
  if (!((s[0] >> 7) & 1)) {
    return 1;
  }
  for (uint8_t i = 0; i <= 4; i++) {
    if (!((s[0] >> (7 - i)) & 1)) {
      return i;
    }
  }
  return 4;
}

int ch_len(char *s) {
  int len = 0;
  for (; s[0] != 0; s += get_byte_len(s)) {
    len++;
  }
  return len;
}

typedef struct {
  char *name;
  char **data;
  int width;
} TableColumn;

typedef struct {
  TableColumn *columns;
  size_t len;
  int records;
  int selected;
} Table;

Table new_table() {
  Table t;
  t.columns = NULL;
  t.len = 0;
  t.records = 0;
  t.selected = 0;
  return t;
}

TableColumn new_column(char *name) {
  TableColumn c;
  c.name = name;
  c.data = NULL;
  c.width = ch_len(name);
  return c;
}

void add_column(Table *t, TableColumn c) {
  t->len++;
  t->columns = realloc(t->columns, sizeof(TableColumn) * t->len);
  t->columns[t->len - 1] = c;
  t->selected = t->len - 1;
}

void add_column_data(TableColumn *c, int len, char *s) {
  c->data = realloc(c->data, sizeof(char *) * len);
  c->data[len - 1] = s;
  int l = strlen(s);
  c->width = l > c->width ? l : c->width;
}

void add_record(Table *t, char **data) {
  t->records++;
  for (int i = 0; i < t->len; i++) {
    add_column_data(&t->columns[i], t->records, data[i]);
  }
}

void set_record(Table *t, int i, char **data) {
  add_column_data(&t->columns[i], t->records, data[i]);
}

void horizontal_line(Table t) {
  for (int i = 0; i < t.len; i++) {
    printf("!");
    for (int j = 0; j < t.columns[i].width + 2; j++) {
      printf("-");
    }
  }
  printf("!\n");
}

void print_table(Table t) {
  horizontal_line(t);
  for (int i = 0; i < t.len; i++) {
    int l = t.columns[i].width;
    l -= ch_len(t.columns[i].name);
    printf("! ");
    for (int j = 0; j < l / 2; j++) {
      printf(" ");
    }
    printf("%s", t.columns[i].name);
    for (int j = 0; j < l / 2 + l % 2; j++) {
      printf(" ");
    }
    printf(" ");
  }
  printf("!\n");
  for (int i = 0; i < t.records; i++) {
    horizontal_line(t);
    for (int j = 0; j < t.len; j++) {
      printf("! %*s ", t.columns[j].width, t.columns[j].data[i]);
    }
    printf("!\n");
  }
  horizontal_line(t);
}

char *fmt_f(double (*f)(double), char *s) {
  double x = atof(s);
  char *buf = malloc(sizeof(char) * 128);
  sprintf(buf, "%.4lf", f(x));
  return buf;
}

double f(double x) { return x * x; }

int main() {
  enable_raw_terminal();

  Table t = new_table();
  add_column(&t, new_column("Аргумент"));
  add_column(&t, new_column("Функция"));

  add_record(&t, (char *[]){"|", "---"});

  printf("\e[1;1H\e[2J");
  print_table(t);

  char c;
  char s[128];
  int i = 0;
  while (read(STDIN_FILENO, &c, 1) > 0) {
    if (c == '' || c == '' || c == 'q') {
      break;
    }
    if (c == '\n') {
      s[i] = 0;
      char *data = malloc(sizeof(char) * (i + 1));
      memcpy(data, s, i);
      t.records--;
      add_record(&t, (char *[]){data, fmt_f(f, data)});
      i = 0;
      s[0] = '|';
      s[1] = 0;
      add_record(&t, (char *[]){s, "---"});
      printf("\e[1;1H\e[2J");
      print_table(t);
      continue;
    }
    if ((c < '0' || c > '9') && c != '.') {
      continue;
    }
    s[i++] = c;
    s[i] = '|';
    s[i + 1] = 0;
    printf("\e[1;1H\e[2J");
    t.records--;
    add_record(&t, (char *[]){s, "---"});
    print_table(t);
  }

  return 0;
}
