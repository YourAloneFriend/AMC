#include "functions.h"


const char* func_str = "y = (a - b) + (c - d) + (e - f) + (g - h)";

const double* single_thread_calc(std::array<const double*, AMOUNT_OF_VERBS>& arr)
{
	double* result = new double(0);
	for (int i = 1; i < AMOUNT_OF_VERBS; i += 2)
		*result += *arr[i - 1] - *arr[i];
	return result;
}

const double* multy_thread_calc(std::array<const double*, AMOUNT_OF_VERBS>& arr)
{
	double* result = new double(0);
	__m128d d__A, d__B, d__C, d__D, d__E, d__F, d__G, d__H,
		d__AB, d__CD, d__EF, d__GH, d__result;

	d__A = _mm_load_pd(arr[0]);
	d__B = _mm_load_pd(arr[1]);
	d__AB = _mm_sub_pd(d__A, d__B);

	d__C = _mm_load_pd(arr[2]);
	d__D = _mm_load_pd(arr[3]);
	d__CD = _mm_sub_pd(d__C, d__D);

	d__E = _mm_load_pd(arr[4]);
	d__F = _mm_load_pd(arr[5]);
	d__EF = _mm_sub_pd(d__E, d__F);

	d__G = _mm_load_pd(arr[6]);
	d__H = _mm_load_pd(arr[7]);
	d__GH = _mm_sub_pd(d__G, d__H);

	d__result = _mm_add_pd(_mm_add_pd(d__AB, d__CD), _mm_add_pd(d__EF, d__GH));

	_mm_store_sd(result, d__result);
	return result;
}

void call_func_and_print_result(std::array<const double*, AMOUNT_OF_VERBS>& arr, const double* (*calc_func)(std::array<const double*, AMOUNT_OF_VERBS>& arr), std::string text)
{
	auto now = std::chrono::system_clock::now();
	const double* result = calc_func(arr);
	auto then = std::chrono::system_clock::now();

	std::istringstream text_print(">>>>>               " + text + "               <<<<<\n");
	std::cout << text_print.str() << std::endl;
	std::cout << "Function:\t" << "y = (a - b) + (c - d) + (e - f) + (g - h)" << std::endl;
	std::cout << "Result:\t\t" << *result << std::endl;
	std::cout << "Time:\t\t" << std::chrono::duration<double>(then - now).count() * 1000 << std::endl;
	std::cout << std::setfill('#') << std::setw(text_print.str().size()) << " " << std::endl;
}

void input(std::array<const double*, AMOUNT_OF_VERBS>& arr)
{
	double temp = 0;
	std::cout << "Input array data" << std::endl;
	for (int i = 0; i < AMOUNT_OF_VERBS; ++i) {
		std::cout << char(97 + i) << " = ";
		std::cin >> temp;
		arr[i] = new double(temp);
	}
}
