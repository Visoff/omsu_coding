void swap_rows(int **matrix, int width, int height, int r1, int r2) {
    for (int i = 0; i < height; i++) {
        matrix[r1][i] ^= matrix[r2][i];
        matrix[r2][i] ^= matrix[r1][i];
        matrix[r1][i] ^= matrix[r2][i];
    }
}
