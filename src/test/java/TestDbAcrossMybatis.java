import com.github.javafaker.Faker;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.platform.commons.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class TestDbAcrossMybatis {

    static Faker faker = new Faker();


    public static void main( String[] args ) throws IOException {
        SqlSession session = null;
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new
                    SqlSessionFactoryBuilder().build(inputStream);
            session = sqlSessionFactory.openSession();
            db.dao.CategoriesMapper categoriesMapper = session.getMapper(db.dao.CategoriesMapper.class);
// Блок WHERE.

            db.model.CategoriesExample example = new db.model.CategoriesExample();
            example.createCriteria().andIdGreaterThan(0L);

// Окончание блока WHERE.
            List<db.model.Categories> list = categoriesMapper.selectByExample(example);

             // Варианы вывода в консоль.
             // System.out.println(Arrays.deepToString(list.toArray()));
             // list.forEach(System.out::print);

            out.println("\nВсего категорий: " +  categoriesMapper.countByExample(example));
            out.println(list.toString());

            db.model.Categories categories = new db.model.Categories();
            categories.setTitle("test");
            categoriesMapper.insert(categories);
            session.commit();

            db.model.CategoriesExample example2 = new db.model.CategoriesExample();
            example2.createCriteria().andTitleLike("%test%");
            List<db.model.Categories> list2 = categoriesMapper.selectByExample(example2);
            db.model.Categories categories2 = list2.get(0);
            categories2.setTitle("test100");
            categoriesMapper.updateByPrimaryKey(categories2);
            session.commit();

            String addcategorytitle = list2.toString();
            out.println("\nВсего категорий: " +  categoriesMapper.countByExample(example));
            out.println("\nДобавлено категория: " + addcategorytitle);

            categoriesMapper.deleteByPrimaryKey(categories2.getId());

            session.commit();
            out.println("\nкатегория : " + addcategorytitle + " успешно удалена");

        } finally {
            session.close();
        }


    }


}
