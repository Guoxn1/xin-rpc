package com.xin.xinrpc.serialize;

/**
 * ClassName: Serializer
 * Package: com.xin.xinrpc.serialize
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/26 下午4:41
 * @Version 1.0
 */
public interface Serializer {

    // 序列化
    <T> byte[] serialize(T obj) throws Exception;

    // 反序列化
    <T> T deserialize(byte[] bytes, Class<T> type) throws Exception;


}
