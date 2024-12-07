package com.xin.xinrpc.server.tcp;
import com.xin.xinrpc.constant.ProtocolConstant;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.Handler;
import io.vertx.core.parsetools.RecordParser;
import lombok.AllArgsConstructor;


/**
 * ClassName: TcpBufferHandlerWrapper
 * Package: com.xin.xinrpc.server.tcp
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/1 下午4:52
 * @Version 1.0
 */

public class TcpBufferHandlerWrapper implements Handler<Buffer> {

    private final RecordParser recordParser;

    public TcpBufferHandlerWrapper(Handler<Buffer> bufferHandler) {
        recordParser = initRecordParser(bufferHandler);
    }

    @Override
    public void handle(Buffer buffer) {
        recordParser.handle(buffer);
    }

    private RecordParser initRecordParser(Handler<Buffer> bufferHandler) {
        // 构造parser
        RecordParser parser = RecordParser.newFixed(ProtocolConstant.MESSAGE_HEADER_SIZE);

        parser.setOutput(new Handler<Buffer>() {
            // 初始化
            int size = -1;
            Buffer resultBuffer = Buffer.buffer();
            @Override
            public void handle(Buffer buffer) {
                if(size == -1) {
                    size = buffer.getInt(13);
                    parser.fixedSizeMode(size);

                    resultBuffer.appendBuffer(buffer);
                }else{
                    resultBuffer.appendBuffer(buffer);
                    // 拼接成完整的Buffer 执行处理
                    bufferHandler.handle(resultBuffer);

                    // 重置一轮
                    parser.fixedSizeMode(ProtocolConstant.MESSAGE_HEADER_SIZE);
                    size = -1;
                    resultBuffer = Buffer.buffer();
                }


            }
        });
        return parser;
    }

}
