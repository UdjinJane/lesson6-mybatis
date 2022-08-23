import api.ProductService;
import com.github.javafaker.Faker;
import dto.Product;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import utils.RetrofitUtils;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;


public class CreateProductTest extends AbstractTestForCreateProd {

    static ProductService productService;
    Product product = null;
    Faker faker = new Faker();

    public String nameOfProduct;
    int id;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        nameOfProduct = faker.food().ingredient();
        product = new Product()
                .withTitle(nameOfProduct)
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() * 10000));
        System.out.println("Add product: " + nameOfProduct);
    }

    @Test
    @DisplayName("Тест 1. POST тестирование создания продукта с подключеным mybatis")
    void createProductInFoodCategoryTest() throws IOException {
        Response<Product> response = productService.createProduct(product)
                .execute();
        id =  response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        // Подчищаем.
        Response<ResponseBody> delresponse = productService.deleteProduct(id).execute();
        assertThat(delresponse.isSuccessful(), CoreMatchers.is(true));


    }



 //  @AfterEach - вынесено в абстракт тест через чистку данных в БД.
 //  void tearDown() {
 //      Response<ResponseBody> response = productService.deleteProduct(id).execute();
 //      assertThat(response.isSuccessful(), CoreMatchers.is(true));
 //  }
}
