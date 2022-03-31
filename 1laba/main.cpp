#include <iostream>
#include <array>
#include <vector>
#include <functional>
#include <chrono>

#define N 20
#define N_DOUBLE 15

template <typename T, std::size_t size>
std::size_t Selection_sort(std::array<T, size> &arr)
{
	std::cout << "\t\t\t\t< Selection_sort >" << std::endl;

	std::size_t amount_of_steps = 0;

	std::size_t j = 0;
	T tmp;
	for(std::size_t i=0; i<arr.size(); i++)
	{
		j = i;
		for(std::size_t k = i; k<arr.size(); k++)
		{
			if(arr[j]>arr[k])
			{
				j = k;
			}
			++amount_of_steps;
		}
		tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	return amount_of_steps;
}

template <typename T, std::size_t size>
std::size_t Insertion_sort(std::array<T, size> &arr)
{
	std::cout << "\t\t\t\t< Insertion_sort >" << std::endl;

	std::size_t amount_of_steps = 0;

	int j = 0;
	T key = 0;

	for(std::size_t i = 1;i<arr.size();i++){
		key = arr[i];
		j = i-1;
		while(j >= 0 && arr[j] > key){
			arr[j+1] = arr[j];
			j = j-1;
			arr[j+1] = key;
			++amount_of_steps;
		}
	}
	return amount_of_steps;
}

template <typename T, std::size_t size>
void run_sort(std::function<std::size_t(std::array<T, size> &)> f, std::array<T, size> arr)
{
	std::cout << "##############################################################################" << '\n';

	auto t_start = std::chrono::high_resolution_clock::now();

	std::size_t amount_of_steps = f(arr);

	auto t_end = std::chrono::high_resolution_clock::now();

	for(auto &x : arr)
		std::cout << x << ", ";
	std::cout << std::endl;

	std::cout << "Amount of steps: " << amount_of_steps << std::endl;
	std::cout << "Time: " << std::chrono::duration<double, std::milli>(t_end-t_start).count() << std::endl;
	std::cout << "###############################################################################" << "\n\n";
}

int main()
{

	std::vector<std::array<int, N>> arr_int_list = {
		{1, 4, 2, 8, 4, 5, 11, 2, 1, 1, 4, 3, 44, 22, -1, 2, 2, 11, 50 ,20},
		{7, 5, 3, 2, 3, 2, 1, 5, 7, 9, 6, 5, 66, 1, 3, 9, 5, 3, 6, 7}}; 

	for(auto &x : arr_int_list){
		run_sort(Selection_sort<int, N>, x);
		run_sort(Insertion_sort<int, N>, x);
	}


	std::cout <<"\n\n--------------------------\n\n\n\n";

	std::vector<std::array<double, N>> arr_double_list = {
		{1.6, 4.2, 2.1, 8.2, 4.6, 5.4, 11.11, 2.22, 1.3, 1.6, 4.5, 3.9, 44.0, 22.56, 1.11, 2.65, 0.001, 11.1, 50.1 ,20.2},
		{7.8, 5.66, 3.35, 2.77, 3.12, 2.22, 1.55, 5.43, 7.77, 9.98, 6.34, 5.33, 66.77, 1.12, 3.45, 9.7, 5.034, 3.33, 6.1, 7.2}}; 


	for(auto &x : arr_double_list){
		run_sort(Selection_sort<double, N>, x);
		run_sort(Insertion_sort<double, N>, x);
	}

	return 0;
}