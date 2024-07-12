package com.openmall.common.json;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

/**
 * JSON 序列化
 * @author wuxuan
 * @since 2024/7/12 09:41:52
 */
@Slf4j
public final class JsonUtils {
    private final static DateTimeFormatter time = new DateTimeFormatterBuilder().appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .optionalStart()
            .appendLiteral(':')
            .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
            .toFormatter();

    private final static DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .append(time)
            .toFormatter();

    private final static DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .toFormatter();

    private final static DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder().append(time).toFormatter();

    /**
     * 前端默认的json格式不包含类型.
     */
    private final static ObjectMapper DEFAULT_OBJECT_OBJECT_MAPPER = ObjectMapperFactory.createObjectMapper();

    /**
     * 反序列化.
     *
     * @param json   json字符串
     * @param tClass 序列化类型
     * @param <T>    序列化类型泛型
     * @return 序列化后的对象
     */
    public static <T> T parse(String json, Class<T> tClass) {
        if (StrUtil.isEmpty(json)) {
            return null;
        }
        try {
            return DEFAULT_OBJECT_OBJECT_MAPPER.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e.getCause());
            throw new RuntimeException(e);
        }
    }

    /**
     * 反序列化.
     *
     * @param file   文件
     * @param tClass 序列化类型
     * @param <T>    序列化类型泛型
     * @return 序列化后的对象
     */
    public static <T> T parse(File file, Class<T> tClass) {
        if (file == null) {
            return null;
        }
        try {
            return DEFAULT_OBJECT_OBJECT_MAPPER.readValue(file, tClass);
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
            throw new RuntimeException(e);

        }
    }

    /**
     * 解析json.
     *
     * @param file json文件
     * @return 序列化后的对象
     */
    public static JsonNode parse(File file) {
        if (file == null) {
            return null;
        }

        try {
            return DEFAULT_OBJECT_OBJECT_MAPPER.readTree(file);
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
            throw new RuntimeException(e);

        }
    }

    public static <T> T jsonNodeToObject(JsonNode jsonNode, Class<T> tClass) {
        try {
            if (jsonNode == null) {
                return null;
            }
            return DEFAULT_OBJECT_OBJECT_MAPPER.treeToValue(jsonNode, tClass);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e.getCause());
            throw new RuntimeException(e);

        }
    }

    public static <T> List<T> jsonNodeToListObject(JsonNode jsonNode, Class<T> tClass) {
        try {
            if (jsonNode == null) {
                return new ArrayList<>();
            }
            final CollectionType collectionType = DEFAULT_OBJECT_OBJECT_MAPPER.getTypeFactory()
                    .constructCollectionType(List.class, tClass);
            return DEFAULT_OBJECT_OBJECT_MAPPER.readValue(DEFAULT_OBJECT_OBJECT_MAPPER.treeAsTokens(jsonNode), collectionType);
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
            throw new RuntimeException(e);

        }
    }

    /**
     * 判断对象的toString()，是否是个json.
     *
     * @param object 对象
     * @return 是否
     */
    public static boolean isJsonString(Object object) {
        if (object == null) {
            return false;
        }
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(object.toString());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 解析json.
     *
     * @param json json字符串
     * @return json的节点结构
     */
    public static JsonNode parseJsonNode(String json) {

        try {
            return DEFAULT_OBJECT_OBJECT_MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e.getCause());
            throw new RuntimeException(e);

        }
    }

    /**
     * 解析json.
     *
     * @param json   json字符串
     * @param tClass 序列化类型
     * @param <T>    序列化类型泛型
     * @return 序列化后的对象集合
     */
    public static <T> List<T> parseList(String json, Class<T> tClass) {
        if (StrUtil.isEmpty(json)) {
            return null;
        }
        final CollectionType collectionType = DEFAULT_OBJECT_OBJECT_MAPPER.getTypeFactory()
                .constructCollectionType(List.class, tClass);
        try {
            return DEFAULT_OBJECT_OBJECT_MAPPER.readValue(json, collectionType);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e.getCause());
            throw new RuntimeException(e);

        }
    }

    /**
     * 序列化Map对象，Key一定为：String.
     *
     * @param json json串
     * @param <T>  值类型
     * @return map
     */
    public static <T> Map<String, T> parseMap(String json) {
        final TypeReference<HashMap<String, T>> typeReference = new TypeReference<HashMap<String, T>>() {
        };
        try {
            return DEFAULT_OBJECT_OBJECT_MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e.getCause());
            throw new RuntimeException(e);

        }
    }


    /**
     * 输出格式化后的json.
     *
     * @param o 反序列化的对象
     * @return 反序列化的json字符串
     */
    public static String toPrettyJson(Object o) {
        try {
            return DEFAULT_OBJECT_OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e.getCause());
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析json.
     *
     * @param o        反序列化的对象
     * @return 反序列化的json字符串
     */
    public static String toJson(Object o) {
        if (o == null) {
            return null;
        }
        try {
            return DEFAULT_OBJECT_OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e.getCause());
            throw new RuntimeException(e);
        }
    }

    /**
     * json的方式进行深拷贝.
     *
     * @param e      拷贝对象
     * @param tClass 目标对象类型
     * @param <E>    类型
     * @param <T>    目标类型
     * @return 拷贝后的对象
     */
    public static <E, T> T jsonDeepCopy(E e, Class<T> tClass) {
        return parse(toJson(e), tClass);
    }

    /**
     * 将java对象转换成map.
     *
     * @param o 需要转换的java对象
     * @return 转成map
     */
    public static Map<String, Object> objectToMap(Object o) {
        final String json = toJson(o);
        final ObjectMapper mapper = DEFAULT_OBJECT_OBJECT_MAPPER;
        final JavaType javaType = mapper.getTypeFactory()
                .constructParametricType(HashMap.class, String.class, Object.class);
        try {
            return mapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e.getCause());
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期反序列化.
     */
    static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            final String text = jsonParser.getText();
            return LocalDateTime.parse(text, DATE_TIME_FORMATTER);
        }

    }

    /**
     * 日期反序列化.
     */
    static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            final String text = jsonParser.getText();
            return LocalDate.parse(text, DATE_FORMATTER);
        }

    }

    /**
     * 日期反序列化.
     */
    static class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

        @Override
        public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            final String text = jsonParser.getText();
            return LocalTime.parse(text, TIME_FORMATTER);
        }

    }

    /**
     * 日期反序列化.
     */
    static class DateDeserializer extends JsonDeserializer<Date> {

        // 2022-09-26T07:19:30.000+00:00
        private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

        @SneakyThrows
        @Override
        public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            final String text = jsonParser.getText();
            // 去掉时区
            String date = text.replace("+00:00", "");
            Calendar calendar = Calendar.getInstance();
            Date sdfDate = sdf.parse(date);
            calendar.setTime(sdfDate);
            // 时区时间加回去
            calendar.add(Calendar.HOUR, 8);
            return calendar.getTime();
        }

    }

    /**
     * ObjectMapper工厂.
     */
    public static class ObjectMapperFactory {

        /**
         * 从默认的redisTemplate中取出对象，需要用此对象来反序列化.
         */
        private final static ObjectMapper DEFAULT_TYPE_OBJECT_MAPPER;

        static {
            DEFAULT_TYPE_OBJECT_MAPPER = createObjectMapper();
            DEFAULT_TYPE_OBJECT_MAPPER.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        }

        public static ObjectMapper createObjectMapper() {

            // 格式化输出
            final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
            builder
                    // 未知属性不失败
                    .failOnUnknownProperties(false)
                    // 空对象不失败
                    .failOnEmptyBeans(false)
                    // 字段可见性设置
                    .visibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                    // 启用以下特性
                    .featuresToEnable(
                            // 枚举写为字符串
                            SerializationFeature.WRITE_ENUMS_USING_TO_STRING,
                            // 允许单个元素匹配到数组上
                            DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
                            // 允许字段名不带引号 {a:1}
                            JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,
                            // 允许用单引号 {'a':'1'}
                            JsonParser.Feature.ALLOW_SINGLE_QUOTES,
                            // 允许json格式化
                            // SerializationFeature.INDENT_OUTPUT,
                            // 不序列化被transient修饰的属性
                            MapperFeature.PROPAGATE_TRANSIENT_MARKER,
                            // 使用java的数组序列化json数组
                            DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)
                    // 禁用以下特性
                    .featuresToDisable(
                            // 未知属性报错
                            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                            // 缺少字段报错
                            DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
                            // 空对象报错
                            SerializationFeature.FAIL_ON_EMPTY_BEANS,
                            // 禁止Date类型转化为时间戳
                            SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            ObjectMapper objectMapper = builder.build();
            objectMapper.findAndRegisterModules();

            // 时间模块
            final SimpleModule javaTimeModule = new SimpleModule();
            javaTimeModule.addDeserializer(ZonedDateTime.class, InstantDeserializer.ZONED_DATE_TIME);
            ZonedDateTimeSerializer dateTimeSerializer = new ZonedDateTimeSerializer(DATE_TIME_FORMATTER);
            javaTimeModule.addSerializer(ZonedDateTime.class, dateTimeSerializer);

            LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(DATE_TIME_FORMATTER);
            javaTimeModule.addSerializer(LocalDateTime.class, localDateTimeSerializer);
            final LocalDateSerializer localDateSerializer = new LocalDateSerializer(DATE_FORMATTER);
            javaTimeModule.addSerializer(LocalDate.class, localDateSerializer);
            final LocalTimeSerializer localTimeSerializer = new LocalTimeSerializer(TIME_FORMATTER);
            javaTimeModule.addSerializer(LocalTime.class, localTimeSerializer);
            javaTimeModule.addSerializer(Instant.class, InstantSerializer.INSTANCE);
            javaTimeModule.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
            javaTimeModule.addDeserializer(Instant.class, InstantDeserializer.INSTANT);
            // 时间反序列化
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
            javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
            javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
            javaTimeModule.addDeserializer(Date.class, new DateDeserializer());
            objectMapper.registerModule(javaTimeModule);

            // 序列化不包含属性为Null的字段
//            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            return objectMapper;
        }

    }
}
