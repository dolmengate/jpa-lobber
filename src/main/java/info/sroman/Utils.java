package info.sroman;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import org.reflections.Reflections;

import javax.persistence.Table;
import java.util.Set;
import java.util.List;

public final class Utils {

    public static Set<Class<?>> getEntityClasses() {
        Reflections reflections = new Reflections("info.sroman.entity");
        return reflections.getTypesAnnotatedWith(Table.class);
    }

    public static Class<?> getRandomEntityClass() {
        List<Class<?>> clazzes = Lists.newArrayList(getEntityClasses());
        return clazzes.get(((int)Math.floor(Math.random() * clazzes.size())));
    }

    public static String getUniqueRandomTableNamesString(int numTables) {
        StringBuilder sb = new StringBuilder();
        String tableName;
        boolean preexisting = false;

        CharMatcher cm = CharMatcher.is(',');
        while (cm.countIn(sb.toString()) < numTables) {
            tableName = getRandomEntityClass().getSimpleName();
            for (String t : sb.toString().split(",")) {
                if (tableName.equalsIgnoreCase(t)) {
                    preexisting = true;
                    break;
                }
            }
            if (!preexisting) {
                sb
                    .append(CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, tableName))
                    .append(",");
            }
            preexisting = false;
        }
        return sb.toString();
    }

    public static Class<?> findEntityClassForTableName(String tableName) {
        Set<Class<?>> clazzes = Utils.getEntityClasses();
        for (Class<?> c : clazzes) {
            String tName = c.getAnnotation(Table.class).name();
            if (tName.equals(tableName)) {
                return c;
            }
        }
        return null;
    }

    public static String findTableNameForEntityClass(Class <?> entityClazz) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, entityClazz.getSimpleName());
    }

    public static String tableClassesToString(Class<?>... clazzes) {
        StringBuilder sb = new StringBuilder();
        for (Class<?> clazz : clazzes) {
            sb.append(Utils.findTableNameForEntityClass(clazz)).append(",");
        }
        return sb.toString();
    }
}
