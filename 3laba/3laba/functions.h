#pragma once

#include <iostream>
#include <iomanip>
#include <functional>
#include <sstream>
#include <intrin.h>
#include <chrono>
#include <array>

#define AMOUNT_OF_VERBS 8
#define MAX_THREAD 4

const double* single_thread_calc(std::array<const double*, AMOUNT_OF_VERBS>& arr);

const double* multy_thread_calc(std::array<const double*, AMOUNT_OF_VERBS>& arr);

void call_func_and_print_result(std::array<const double*, AMOUNT_OF_VERBS>& arr, const double* (*calc_func)(std::array<const double*, AMOUNT_OF_VERBS>& arr), std::string text);

void input(std::array<const double*, AMOUNT_OF_VERBS>& arr);