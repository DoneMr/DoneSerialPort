# Android 平台串口库以及Tcp客户端库
---
使用串口必须保证应用具备root权限

必须先知道串口的参数以及串口的路径，可直接使用adb shell命令:<font color="#FF0000">ls /dev</font>（一般情况下，每个设备串口资源2-4个左右，名称一般为/ttyS0,/ttyHLS0等等）

socket 创建客户端，纯功能，初步接触的同学可以参考源码也可以直接使用，有问题issue即可
```java
class SerialPortDemo{
    
    public static void main(String[] args){
        initSerialPort();   
    }
    
    private static void initSerialPort() {
    	SerialPortSettings settings = new SerialPortSettings(); //配置
    	settings.setmDevFile("/dev/ttyMT1"); //串口路径，其余参数可默认，也可单独配置
    	serialPorter = new SerialPorter(settings, new OnSerialListener() { //监听接口
    		@Override
    		public void onConnect() {
    			showToastForHandler("串口已连接");
    		}
    
    		@Override
    		public void onDisconnect(int code, String message) {
    			showToastForHandler("串口已断开");
    		}
    
    		@Override
    		public void onReceive(String hexData, byte[] srcData) {
    			showToastForHandler("串口收到数据:" + hexData);
    		}
    
    		@Override
    		public void onError(int code, String message, Object data) {
    			showToastForHandler("串口错误:" + code + "," + message);
    		}
    	});
    	serialPorter.begin();
    }
    
}

```