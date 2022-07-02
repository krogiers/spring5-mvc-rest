package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, 
                     VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        LoadCustomers();
        LoadCategories();
        LoadVendors();
    }

    private void LoadVendors() {
        vendorRepository.save(Vendor.builder().name("Vendor 1").build());
        vendorRepository.save(Vendor.builder().name("Vendor 2").build());
        vendorRepository.save(Vendor.builder().name("Vendor 3").build());
        vendorRepository.save(Vendor.builder().name("Vendor 4").build());

        log.info("Vendor Data loaded = " + vendorRepository.count());
    }

    private void LoadCustomers() {
        customerRepository.save(Customer.builder().firstName("Kurt").lastName("Rogiers").build());
        customerRepository.save(Customer.builder().firstName("Tina").lastName("Seyneave").build());
        customerRepository.save(Customer.builder().firstName("Tibo").lastName("Rogiers").build());
        customerRepository.save(Customer.builder().firstName("Britt").lastName("Rogiers").build());

        log.info("Customer Data loaded = " + customerRepository.count());

    }

    private void LoadCategories() {
        Category fruits = new Category();
        fruits.setName("fruits");

        Category dried = new Category();
        dried.setName("dried");

        Category fresh = new Category();
        fresh.setName("fresh");

        Category exotic = new Category();
        exotic.setName("exotic");

        Category nuts = new Category();
        nuts.setName("nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        log.info("Category Data loaded = " + categoryRepository.count());
    }
}
