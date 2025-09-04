package com.example.web.tools.alipay;

public class AlipayConfig {
  
  
  // 商户appid
  public static String APPID = "9021000135645491";
  //支付宝私钥
  public static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCxvpIqoODjhpKFaT3p9dfU4tP2JT8OF9sGSRBrAZs+NBVzjYAJcOeRjtn9z/11z+xYEXP5OjEbArTLjd15mbBOtA0lE6O1n3MDBFezY+BCCnAdCSvk8BTcyk6wifqrrYpH+S5Yl5JmLDgyIRIh14usw1DLncAZCRrx1/w7exNWNRs2thTwiU96y5e4Gfjh7Nkq0TgwpaCO3kTscaZrnuQU/3KFXiz0NUt1Ev+Ls4Hd0J3Z0swk0/SW4GbHKXNRfZ2OUsGt16CT9pXz2NYWLeit7xZouKMlKiAvWtB5uYsCtdBt0/2h8iTq8dQQh6gMkqtT/5j2e8R7M1fOZd5n4RvZAgMBAAECggEAfroG8Mm/Tbl24hPFBvg3KBuJUGgB6wRrLrINOJ3ul4z+9mIetZyAHjGhjusqJUNXmPromvs4Y6+sQ60kCTzgjVymOpckE6xZjf8coFRHYg6bHLibSSE7XqgHLqtRw/F5NBOfTZ7TzHEep+8YRHSvqz+ebW5Ntgvd4CxENAxuHvmPef7SNjoSAWGB9DYR8QMAHIupuVRgDvupqjw+AirpFGoUqFVMpcX/3YaryAIQIaSE4zXAuikHJW2z5f6ZHz3U4VsiH6qdfzQNH25ZIk0v4IGJ9LM9RWgX/bTiVz6EU1zWqaRRU1ADAqdnCNyAnp1xCMuWkvsqXhZMPsaIp8hYAQKBgQDykuUJHmn92S3B1DxRKDXXjA8oKJF2ehsjdGDsGOwDItp7Gi/c+VrPeac235Iu7lRWEbzZJN7zm65+DQQKVmfbcmQR7noW5MyRc0aet7xyi8dC2+z/WypfwIkvPAVpVm9R/+GFCm0q8AfzhLqT6c662RTs5gQZLRvNe6WeAjcSWQKBgQC7lRaMZrnzJdfkWDZgj8DiQyTG+PT19bXQVxsPNWHlXY3sJvAXIzIGlgoRlz78MpMc6AjHVa3Le/Z6tF5qczIjMm2TwfgerJ/dGhatqZlS6CC7/XQy+mQMiKbfmXT/Le3WLKsAP2nxTFNVcz90sra0Y0+SfIVQl47ub56l5QolgQKBgEgpOslDqvRBL0OCB499DsdYewnPR7afDIO3kz2pDEzCiYwldieeZk1iO0QvriUX8S7JiAxw04JUxcvpw1Hv8mVt2icbcmZtGg4K5O9CaWUJ//T7x70POcPSWv0aNeHoPKWAIW6ac29aiIMA8SAfZchf28G9i2qR6LtwFQy0S/3BAoGBAJNgZSDWLM5uMxrCe6aOCvs5yZFSVBNSgxA2/5mJVWrBgBnD1eo3yrwF8c80R1YZ3h9d5lfGZw9yS4rIX02/R+sDWbg3M1vWQJuUPbEfiMjse5t3XG4Y77YxPvp6NMOwMCaPXaKKMBBW6To/m+Fx7732MLRkhNF24eNPaxPnyXsBAoGBAIWP/ZmVVLGZuES01nrzTIMV/c2fj7NSbY4FVD8ymM1N1PEfrv33xseDe39A+Z9zvcCJXnpKXOqtIzHp9ENrE/AtBgXRineDP0UUTjC5v6EetBwE45u5y5KzL7OBtQKvSZaXTVg7typS4Gn7UENYccCdYyGOiOcXXJLg+DyW2MdC";
  //支付宝公钥
  public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjEZh/2i90xz0JhYZjxcOQ6L3rhPUbh+AB4+uQCMz/xA/OhG4x8hDr5MHbJqCc53Utcn5swtCYteDhvxvwQtgutYXCEVv73NG2hubUjbtoCq/OzWvrGNFothUA/4K/Re+BGypjUguvRYrjcptC9fxvx4QLD9ZPrqmiCPsUK7UmvCm7LbvFrBo+IP7gEOY/IkNohH6PU/iZ4IhrKcQMt541B0d69Ra17V4AsFkxqe+O09XrXKZ20JO00lFdcgJknAoFuIdszD4Y79Q63yT4ZxWd39Ad82jrwqpLLwzxK4fcGxktE3yDBy19dEc+FsEKFLTZwG6wLr5nMPkGPdJMozuiQIDAQAB";
 
  // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
  public static String off_line_notify_url ="";
  // 商户可以自定义同步跳转地址
  public static String return_url = "";
  // 线上请求网关地址
  public static String URL = "https://openapi.alipay.com/gateway.do";

  // 编码
  public static String CHARSET = "UTF-8";
  //  public static String CHARSET = "GBK";
  // 返回格式
  public static String FORMAT = "json";
  // 日志记录目录
  public static String log_path = "/log";
  // RSA2
  public static String SIGNTYPE = "RSA2";
}