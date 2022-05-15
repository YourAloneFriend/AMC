#include "functions.h"

//25: y = (a - b) + (c - d) + (e - f) + (g - h)

int main()
{
	std::array<const double*, AMOUNT_OF_VERBS> arr;


	input(arr);
	call_func_and_print_result(arr, single_thread_calc, "single_thread_calc");
	call_func_and_print_result(arr, multy_thread_calc, "multy_thread_calc");


	return 0;
}