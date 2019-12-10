#include <stdio.h>

#define WIDTH  25
#define HEIGHT 6
#define PIXELS (WIDTH * HEIGHT)

int leastZerosLayer(char layers[][WIDTH * HEIGHT], int n);
char layers(char str[], int n);

int main(int argc, char* argv[]) {

  // Open up the input file and read it into a char array:
  FILE *file = fopen("input", "r");
  fseek(file, 0L, SEEK_END);
  int n = ftell(file); // maybe not needed, but let's pretend to care about mem.
  fseek(file, 0L, SEEK_SET);
  char str[n];
  fgets(str,n,file);
  fclose(file);

  // Put the char array into layers:
  int total_layers = (n - 1) / PIXELS; // off-by-one errors blow
  char layers[total_layers][PIXELS];
  for (int i = 0; i < total_layers; i++) {
    for (int j = 0; j < PIXELS; j++) {
      layers[i][j] = str[i * PIXELS + j]; // 1-D to 2-D conversion
    }
  }

  // Count the layer with the least 0's:
  int least_zeros_layer = leastZerosLayer(layers, n);

  // Part 1:
  int ones_count = 0;
  int twos_count = 0;
  for (int j=0; j < PIXELS; j++) {
    if (layers[least_zeros_layer][j] == '1') {
      ones_count++;
    }
    if (layers[least_zeros_layer][j] == '2') {
      twos_count++;
    }
  }

  printf("Part 1: %d\n", ones_count * twos_count);

  int final_layer[PIXELS];
  for (int i=0; i < PIXELS; i++) {
    char pixel_color = '2'; // transparent to start
    for (int j=0; j < total_layers; j++) {
      if (pixel_color == '2' && layers[j][i] == '1') {
        pixel_color = '1';
      }
      if (pixel_color == '2' && layers[j][i] == '0') {
        pixel_color = ' '; // Use an invisible char for readability
      }
    }
    final_layer[i] = pixel_color;
  }

  // Part 2: Print the image to the screen
  for (int i = 0; i < HEIGHT; i++) {
    for (int j = 0; j < WIDTH; j++) {
      printf("%c", final_layer[i * WIDTH + j]);
    }
    printf("\n");
  }

  return 0;
}

int leastZerosLayer(char layers[][WIDTH * HEIGHT], int n) {
  int layer = 0;
  int least_zeros = PIXELS + 1;
  int total_layers = (n - 1) / PIXELS; // off-by-one errors blow
  for (int i=0; i < total_layers; i++) {
    int zero_count = 0;
    for (int j=0; j < PIXELS; j++) {
      if (layers[i][j] == '0') {
        zero_count++;
      }
    }
    if (zero_count < least_zeros) {
      least_zeros = zero_count;
      layer = i;
    }
  }
  return layer;
}
