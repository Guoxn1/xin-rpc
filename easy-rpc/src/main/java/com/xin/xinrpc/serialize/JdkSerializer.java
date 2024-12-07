package com.xin.xinrpc.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * ClassName: JdkSerializer
 * Package: com.xin.xinrpc.serialize
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/26 下午4:42
 * @Version 1.0
 */
public class JdkSerializer implements Serializer{
    @Override
    public <C> byte[] serialize(C obj) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream =  new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.close();
        return byteArrayOutputStream.toByteArray();


    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        try{
            return (T)objectInputStream.readObject();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            objectInputStream.close();
        }
    }
}
