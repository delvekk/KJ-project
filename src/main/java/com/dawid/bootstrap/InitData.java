package com.dawid.bootstrap;

import com.dawid.domain.Category;
import com.dawid.domain.Product;
import com.dawid.domain.Role;
import com.dawid.domain.User;
import com.dawid.repositories.CategoryRepository;
import com.dawid.repositories.ProductRepository;
import com.dawid.repositories.RoleRepository;
import com.dawid.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Component
@Profile("h2")
public class InitData implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;


    public InitData(CategoryRepository categoryRepository, ProductRepository productRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        Category category = new Category();
        category.setId(1L);
        category.setDescription("Smartfony");
        categoryRepository.save(category);

        Role role = new Role();
        role.setId(1L);
        role.setRole("USER");
        roleRepository.save(role);

        Role role1 = new Role();
        role1.setId(2L);
        role1.setRole("ADMIN");
        roleRepository.save(role1);


        User user = new User();
        user.setId(1L);
        user.setActive(1);
        user.setAddress("");
        user.setConfirmationToken("abc");
        user.setName("ADMIN");
        user.setLastname("ADMIN");
        user.setEmail("delvekshop@gmail.com");
        user.setPassword(new BCryptPasswordEncoder().encode("admin"));
        user.setRoles(new HashSet<>(Arrays.asList(role1)));

        userRepository.save(user);


        Set<Category> categories = new HashSet<>(Arrays.asList(categoryRepository.getOne(1L)));

        Product product = new Product();
        product.setId(1L);
        product.setDescription("Flagowy Samsung Galaxy S8 Midnight Black przełamuje schematy i " +
                "na nowo definiuje klasę premium. " +
                "Na przeprojektowanym ekranie QHD+ dostrzeżesz znacznie więcej i jeszcze lepiej przeżyjesz fabułę filmu. " +
                "Zrobisz też niebywale ostre zdjęcia, zarówno aparatem głównym jak i selfie, " +
                "bo teraz każdy z nich posiada szybki autofocus. Niezawodną ochronę danych gwarantuje " +
                "nowoczesny duet – czytnik linii papilarnych oraz skaner tęczówki. " +
                "Tobie pozostaje korzystać z Galaxy S8 Midnight Black bez względu na pogodę. " +
                "Zadba o to odporność na pył i wodę klasy IP68.");
        product.setAmount(0);
        product.setDiscount(true);
        product.setImage_url("s8_image.jpg");
        product.setName("Samsung Galaxy S8");
        product.setPrice(2000);
        product.setRecent(false);
        product.setCategories(categories);
        productRepository.save(product);


        product = new Product();
        product.setId(2L);
        product.setDescription("Wyświetlacz FullView 5,9” zajmuje niemal cały front smartfonu Mate 10 Lite. " +
                "Co więcej, ekran niemal nie posiada ramek, co daje wrażenie nieograniczonego obrazu. " +
                "Wysoka rozdzielczość FHD+, w połączeniu z proporcją 2:1, całkowicie zmienia sposób odbioru wyświetlanego obrazu. " +
                "Czytanie treści w internecie i mediach społecznościowych jest wygodniejsze, a filmy oraz gry bardziej realistyczne. " +
                "Mało tego, praca z wieloma aplikacjami na " +
                "podzielonym ekranie również staje się przyjemniejsza i bardziej intuicyjna.");
        product.setAmount(20);
        product.setDiscount(true);
        product.setImage_url("p10_image.jpg");
        product.setName("Huawei P10");
        product.setPrice(1200);
        product.setRecent(false);
        product.setCategories(categories);
        productRepository.save(product);


        product = new Product();
        product.setId(3L);
        product.setDescription("Futurystyczna konstrukcja iPhone X 64 GB Gwiezdna szarość " +
                "nie posiada już przycisków nawigacyjnych u dołu ekranu. " +
                "Udoskonalony iOS sprawi, że każdą czynność wykonasz gestem. " +
                "Apple spełniło marzenie o inteligentnym smartfonie, potrafiącym reagować na Twój dotyk, głos, " +
                "a nawet na spojrzenie. iPhone X jest niemal w całości ekranem. " +
                "Matryca Super Retina 5,8” o rozdzielczości 2436 x 1125 px pokrywa praktycznie cały przedni panel smartfonu. " +
                "Odwzorowuje przy tym kolory w sposób naturalny, w wysokiej jakości i kontraście 1 000 000:1.");
        product.setAmount(40);
        product.setDiscount(false);
        product.setImage_url("iphoneX_image.jpg");
        product.setName("Iphone X");
        product.setPrice(4000);
        product.setRecent(true);
        product.setCategories(categories);
        productRepository.save(product);


        product = new Product();
        product.setDescription("Samsung Galaxy S9+ Midnight Black dokonał przełomu, wprowadzając zupełnie nowe możliwości " +
                "do fotografii mobilnej. Teraz przeżyjesz na nowo to, co zostało utrwalone na zdjęciach w sposób równie emocjonujący i " +
                "równie realny. Nowatorskie technologie zamknięto w genialnej, lekkie a przy tym smukłej obudowie z ekranem 6,2” " +
                "Infinity 18,5:9, który praktycznie nie posiada ramek. Opowiedz zatem swą historię zdjęciami, " +
                "dodaj do tego dźwięk stereo z głośników AKG i dzień za dniem odkrywaj perfekcję Galaxy S9+.");
        product.setAmount(40);
        product.setDiscount(false);
        product.setImage_url("s9_image.jpg");
        product.setName("Samsung Galaxy S9");
        product.setPrice(3500);
        product.setRecent(true);
        product.setCategories(categories);
        productRepository.save(product);




    }
}
