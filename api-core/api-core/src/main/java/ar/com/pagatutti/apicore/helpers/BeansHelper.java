package ar.com.pagatutti.apicore.helpers;

import ar.com.pagatutti.apicore.helpers.LocalDateConverter;
import ar.com.pagatutti.apicore.helpers.LocalDateTimeConverter;
import ar.com.pagatutti.apicorebeans.enums.AppMessage;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;


public abstract class BeansHelper {

    private static final Logger LOG = LoggerFactory.getLogger(BeansHelper.class);
    private static final List<String>
        ENTITY_FIELDS_EXCLUDED = Collections.singletonList("id");

    public static Map<String, String>
        translateColumnsForEntity(Object entity) {

        // Get entity fields in a list
        List<Field> fields = extractPropertiesFromBean(entity);
        Map<String, String> fieldsMap = new HashMap<>();

        for (Field field : fields) {

            // Property name will be the key and column name the value.
            // If doesn't exist column name, property name is placed as value
            Column annotatedColumn = field.getAnnotation(Column.class);

            String columnName = null == annotatedColumn ?
                field.getName() : annotatedColumn.name();

            // Some column names are excluded to avoid accidental overwriting
            if ( ENTITY_FIELDS_EXCLUDED.contains(columnName.toLowerCase()) )
                continue;

            fieldsMap.put(field.getName(), columnName);
        }

        return fieldsMap;
    }

    public static Map<String, String>
        extractColumnsFromEntity(Object entity) {

        // Get entity fields in a list
        List<Field> fields = extractPropertiesFromBean(entity);
        Map<String, String> fieldsMap = new HashMap<>();

        for (Field field : fields) {

            // Property name will be the key and column name the value.
            // If doesn't exist column name, property name is placed as value
            Column annotatedColumn = field.getAnnotation(Column.class);

            String columnName = null == annotatedColumn ?
                field.getName() : annotatedColumn.name();

            // Some column names are excluded to avoid accidental overwriting
            if ( ENTITY_FIELDS_EXCLUDED.contains(columnName.toLowerCase()) )
                continue;

            fieldsMap.put(columnName, field.getType().getSimpleName());
        }

        return fieldsMap;
    }

    public static void populate(Object bean, Map<String, ?> properties)
        throws InvocationTargetException, IllegalAccessException {

        // Registering custom converters
        ConvertUtils.register(new LocalDateConverter(), LocalDate.class);
        ConvertUtils.register(new LocalDateTimeConverter(), LocalDateTime.class);

        // Copying properties from map
        BeanUtils.populate(bean, properties);
    }

    public static List<Field> extractPropertiesFromBean(Object bean) {
        return extractPropertiesFromBean(bean, true);
    }

    public static List<Field> extractPropertiesFromBean(Object bean, boolean extractAsEntity) {

        // Get bean fields in a list
        List<Field> fields =
                new ArrayList<>(Arrays.asList(bean.getClass().getDeclaredFields()));

        // Get superclass of entity
        Class superClass = bean.getClass().getSuperclass();

        // If entity has superclass, their properties will be considered
        if ( null != superClass ) {
            fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
        }

        if ( extractAsEntity ) {
            // Entities types (relationships) are not considered
            // and Collection types neither
            Predicate<Field> hasRelationship =
                    f -> f.getType().isAnnotationPresent(Entity.class);
            Predicate<Field> isACollection =
                    f -> Collection.class.isAssignableFrom(f.getType());

            // Filtering not allowed fields
            fields.removeIf(hasRelationship.or(isACollection));
        }

        return fields;
    }

    public static Optional<Field> findFieldByName(Object bean, String property) {
        return
            extractPropertiesFromBean(bean, false)
                .stream()
                .filter(p -> p.getName().equalsIgnoreCase(property))
                .findFirst();
    }

    public static boolean copyProperties(Object target, Object source) {
        try {
            BeanUtils.copyProperties(target, source);
            return true;
        } catch (IllegalAccessException|InvocationTargetException e) {
            LOG.error(AppMessage.E_COPYING_BEANS.getMessage(), e);
        }

        return false;
    }

    public static Optional<Object> extractPropertyValue(Object targetObject, String propertyName) {

        Class<?> objClass = targetObject.getClass();

        try {
            Field field = objClass.getField(propertyName);
            return Optional.of(field.get(targetObject));
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }

        return Optional.empty();
    }

    public static Map<String, Long> backupIdFromNestedEntities(Object entity) {

        Map<String, Long> backup = new HashMap<>();

        // Get all fields from entity
        List<Field> fields =
            new ArrayList<>(Arrays.asList(entity.getClass().getDeclaredFields()));

        // iterate over all fields
        for (Field field : fields) {

            // Exclude non-entity fields
            if (!field.getType().isAnnotationPresent(Entity.class))
                continue;

            String fieldName = field.getName();

            try {
                // Getting nested entity
                Object nestedEntity = PropertyUtils.getProperty(entity, fieldName);

                if ( null == nestedEntity )
                    continue;

                // Getting id of nested entity
                Object id = PropertyUtils.getProperty(nestedEntity, "id");

                if ( null != id )
                    // back up id of nested entity
                    backup.put(fieldName + ".id", (Long) id);

            } catch (IllegalAccessException |
                     InvocationTargetException |
                     NoSuchMethodException e) {
                LOG.error("Error at performing backup of '{}' from '{}'.",
                          fieldName, entity.getClass().getSimpleName());
            }
        }

        return backup;
    }

    public static void restoreIdToNestedEntities(Object entity, Map<String, Long> backup) {

        // Iterate over backup map
        for (Map.Entry<String, Long> item : backup.entrySet()) {

            String propKey = item.getKey();

            try {
                Object currentVal = PropertyUtils.getProperty(entity, propKey);

                // Only will be taken in count those properties whose has null values
                if ( null == currentVal )
                    PropertyUtils.setProperty(entity, propKey, item.getValue());

            } catch (IllegalAccessException |
                     InvocationTargetException |
                     NoSuchMethodException e) {
                LOG.error("Error restoring '{}={}' to {}",
                          propKey, item.getValue(),
                          entity.getClass().getSimpleName());
            }
        }
    }
}