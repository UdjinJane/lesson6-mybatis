import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import utils.MyDButils;

import java.io.IOException;

public abstract class AbstractTestForCreateProd {
    private static MyDButils myDButils;
    private static long maxProductsId;
    private static long maxCategoriesId;


    // public static MyDButils getMyDButils() {
    //    return myDButils;
    // }


    @BeforeAll
    public static void init() throws IOException {
        myDButils = new MyDButils();
        myDButils.initSession();
        maxCategoriesId = myDButils.getMaxCategoryId();
        maxProductsId = myDButils.getMaxProductId();

        //Добавляем тестовую категорию.
        myDButils.addNewCategoryWithTitle("TestCategory");
    }

    @AfterAll
    public static void finish() {
        // Чистим последний добавленный продукт
        myDButils.deleteExtraProducts(maxProductsId);
        // Чистим последнюю добавленную категорию.
        myDButils.deleteExtraCategories(maxCategoriesId);
        // Чистим все тест категории.
        myDButils.closeSession();
        System.out.println("\n++++++++ data clean complete ++++++++");
    }
}
