package com.graduation.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2021000116695211";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDCce3mYv8DliRnUWhSdtB5w+X1Obs4LoSiU1Ln6wHC2IEdcCUT/xWYFVUKvuhMThV4oWqNqz0AHQ8qTXeybokIlGd19bpGGyFHH44tozL9vz1sZbVnvySA7W2UVJuIMdKvkFlWzeCPe3Nnw7amu4VKBRW6YsYxm2u4O9AoEtpaVG8b0QAqYXZoUvz01fjzNFwrXrryAnUgX4ilkfSeo06gfRWSzzlk8q2YiPVhhgaRDuTqkhD21IvXz5uRc7mikyk21mJUHI6LpPP8Z5nOJohIBrTpFrA3tzDO1BUVU+rHau5NV0CgjRt7SVbVADi2qzXzf6dlEt8gydrKRCXOF32/AgMBAAECggEAcHcZFNJZkad78s5hOqFCP8uQ7F+x6/LsAD+VilKhOCOlunMs3v4BbW+ZOpflII1hWd5zFPHgXHpyVRNtkiTg2beMh+vTC82RV+PmhnsIeT/ttQ62+ATUhkXNxaeSNhorNCSXp994bUUIIRJQSOvZUlDSmnWbEQJGMc9WvHb7GJcOwGIXjZLADUlKrUgGOKwhF4ziy6XtY1JPJ9Vq/Mhys/YNvgewMQes7TSkrVpMyIXCW8BD4H5CJ8uc8a0/h37MLxh06pk5ueWOVyOqnHiWwsZaIxFA7D1bURq47YbBlSZoWJ4b0cf4YpUgSJAcUmgpMxmysXotuGWaLCijq1rx0QKBgQDwneZD6txbyywq8aetpxLCwpyP464k1tPg2m/G4pBc5c4AoAGleCPgwhls5f4yeGwCYbIDj9t6jWBQLCM2JkneRkcRwF6Wp3Mr5zos5hoXtgmFu9+UAVMOtv68D4tIy6dlNzhoRXfVJglfpnYZXCXnhbmfRTx7VOZ+pjffrAptwwKBgQDO4FnYW9C0qXzxHYq9dGS+n5jxM5O48gIRpcvIHpJR1FegGRYD0B6gnAU4NbuZrCg4lAOsTuAWqo2Etg8CcnpaSNMKDKgZH/DyJxMFTltTjD2wIf632Ds1rN7UWq2OmfWAa+Mx4a7IIemmPGG0CUW0QBKvl0ZbBsUnem8Q5s4EVQKBgBWTw1Nx20LsDFDpjMTYx6jpdMq+ex5YmXV/gSiPpqx9+yQ5NQyWonkETu1iDpKOE/l+s0z4eJEb3ngyDRJNCrDBEpx36MOPzpcwTBo8pDheHap9d4Y5PC6EoIFSz4W+pYtBEXDgUeeuWobWUZ7ikY+agVPUDKFusqDG3RnUvrarAoGAD+P3wsC6tUXXjQXB5G+OVX4Y114o2KnuMsDTeFmwz4xJ9sg2grB3ycpyNDEcad9pb38fERs150fOewUo/f0hHNI/M6RpPRTRAyixM1UjerVo/6B82k5HrgXLT/BvKGb3Dpg66Vf2fvTr55L3xkZ4DjJlvQsXomJQvomPAnNYG8kCgYEA4y3hg+DzM+GmRmtCQB5DOALWTNQCqfHpwU6n7X+30t8rA1ilj0pqMJpTWr4GG2DPLRsIBqF6S0iUw0Wwxc3rK2YyOkOD2M3dCfsi/ODy6JDNlEPvCI1or3X8xG2fx56Vai3lEMBMdaULHr9Oeyo/AZZpVneH8KV94S/gxsXj/5o=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkVQGJu2sk/g0eMlJ9K0IseJm/y1Y+5TkYqjRWqS9dHdxxGzMVJDtQoCpyIJUQ7N4dth/HYpzyzS5DIU/pBnHSM5z3Vc2dS0oEvXLyKOUBhR/O8QOy9JCocJzU++ylnsfIt88SMLGMgwOPHt/1cTD/xEztdBpjhaGp88U/mcNpZt9+qTNPc5afZAB+86dQRDhIF9k0GkqEQdQxi+LSrJAnBdf5SfMFHw3RD14Tz88+rnckTEsZzNFVS5pNrSR3qOiM4r3TFBqsCMjyqDbOl3Cv69Enc4S36lKiMhMhw7T3w3A33rVm5VKFpLBzNysDIPrxtnTMx38wT9rufnmf6ic9QIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:9000/paycheck/paySuccessDo";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:9000/paycheck/paySuccessUrl";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
//	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
//    public static void logResult(String sWord) {
//        FileWriter writer = null;
//        try {
//            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
//            writer.write(sWord);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (writer != null) {
//                try {
//                    writer.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}

