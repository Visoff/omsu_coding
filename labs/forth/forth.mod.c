void fill_2d_arr_with_sum(int **arr, int w, int h) {
    for (int i = 0; i < w; i++) {
        for (int j = 0; j < h; j++) {
            arr[i][j] = i + j + 2;
        }
    }
}
