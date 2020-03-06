package com.itheima.wechat;

import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.itheima.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author liam
 * @description 支付控制器
 * @date 2020/3/6-19:10
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    private Logger log = LoggerFactory.getLogger(PayController.class);
    private String serverUrl = "https://openapi.alipaydev.com/gateway.do";
    private String APP_ID = "2016101800718449";
    private String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC09Be1Z/fEZSFp3O8aBbicLGrMSeYoGzXZrLkaCt999KWk+2oEggqZz0sZ4aNJRfTJuMovu+ucH2cOeI7ld3B25wgwddz3MZd5GhSGGmcz5G1AgPHvy28VdJnh1oSpcHT9G6tTDhsHC2niQ1I0i62dbe/anchXUqMltVfrDUf/A1H9Saf4qSiVGBoUYIlEWGnFfOFgoVtFw1656mohQF9JcgmX1SwC3/bGK/pLzjFRPdSPG1knhMDhb0yQky6X2ZDy4o1UMHB9eldTsfzyaK6cXTXydZ9BCXL/KM+Lb/swc4dqzsMW2RVhPolR4WcC9km457rFOvhOLNTqBBsYt2qFAgMBAAECggEBALNYAW+AHeb+vIEpwaiu5uuScj8LNxuk7FhTghm3kQ5LPSkpLUhOgqcy9qIuKiHjPdv4VBu/SqMv1HpZLndrS4HtNQaiqmAtPI2ANbs7y/F4gned1SvvGqfPKvzUGiO9G5YIqGLm5g6zVzwzsPP3RBl17nVkUD2L37rt4rdKMIMjYQuk2uy7/jmZX+o7oXg+I4PtInloWhwwIQ8wnJg7zXfyeEp4Kin4Dm/c6i0D/3rXX3kuVLa+v+N/qNkuWgKHo45EHQkv369vny7JQgJ1Z+yIIKipK/+nZ+xjcsjZLv/APac7UTsslPxNE4g0+pqHpeznjI+I+27mWLEcUnB98jkCgYEA6tFeTfxjEKUEX2D8Oba6mnx2VlgHkGTburM4RKzWf6JhUnlzq8SmXya83wYIlKD9LOuNBVStpJQvIhVs4YSzN603YQBb7O75j0XZoN1W8y05CsOoSW5vfPG3jqYyYhEtcvUcqEEyOIx5t3m53vkRswPG+JEd3J6ZRh6AE9pgb8sCgYEAxUbWplpM8IrsKTsRNu/HXgFtHdPI5HB5zRCtKliwcmTsaFZWQgyyxriW8c2QJqcQ6sutvVenl6NmeTlTdIn83uOIZfeR2yRQDqn22Jhs8mw3d95anFgaD7P2dIxfmJETkDWPU02JmiZKFv4TOxNp+4PBCR4/27hFi9lcfc8TpO8CgYB0OnPaWB3U5Ek4yLGwA4Fz7D1K2cHMuA+G5yTn4UE+5eoUP6eAkxLisDL2lW7z2ofWjzU7OUHF389AqKg/LEblPSjXK61UpqkCQMnK9SQPtAezC0umJTKr/p0jz6HKpsP/TdIEeljYYrfwmCDnfBNFr43yBSppxFsLuRcePI5VZwKBgQCadpbG9dlcVkoHFnqqGyBtGIzJV/Pp+a9EwxPzHjI5v4/jOgab/LlWI138wklmGlWCoivNHv7YGla4AAy6KPjXxCweOrP3c/1DHg1rFh3nype7wVwNImZ1eSE1rFuFZ8J7nZQ1U3a0PdqxGgsQrGjFSCoy635wmV3K3EwxLZxh/QKBgFZ4wOqHROi7jqbEq6TTaO0mVpsOocXq4Oe0d50ML4qmZJgHaJthMqsCFUrSVClfEAKFAX4rZJ4vRQmPJO9BzIUP9DRh6ucOacfBOsL/3fB0bv51Y4kTRf4YZhOoG5iRz78wTZTIBqwm8z+wEaQMhuPQkr3rG4a0LA1A4Ncg2MMc";
    private String CHARSET = "UTF-8";
    private String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyWaipDkgOsrtWIZsgUEt/yKWGGm8/EQaPQEyjpz2uOR3e6in/onPxrA0Z6FUN98Gt/avt4RpYsTglBDMLMrqFizP9lorj2aC3eEA56WUxn3cDRRCvVjtbCgNgLduRJ73a49dlQr9/m9qbBxDuLTEQPuXxWpZOIsbrwN1zBD9q2dRqs2f4OAQiKwgdehLUaRAOt0GTcI8u+b2oAbuE/EGMjkHKG1hT1JApj+U7HP+Fd+fqU0V/gx+gfPBnk9ZwPwMMuxjFqTL1PzpY+wn9XdA5ufXHbuERDSQ74/Hj3HPabqW+XtMl/AEGycLzzKd7pb5ig58j+EwOpBih5X3nLys8wIDAQAB";

    @Reference
    private OrderService orderService;

    @RequestMapping("/aliPay")
    public void aliPay(HttpServletRequest httpRequest,
                       HttpServletResponse httpResponse,Integer orderId) throws ServletException, IOException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
        //创建API对应的request
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        //支付成功之后的跳转地址
        alipayRequest.setReturnUrl("http://exhcnr.natappfree.cc/pages/orderSuccess.html?orderId=" + orderId);
        //通知我们支付结果的地址
        alipayRequest.setNotifyUrl("http://exhcnr.natappfree.cc/pay/alipayNotify.do");
        Map map = orderService.findById(orderId);

        JSONObject bizJson = new JSONObject();
        String outTradeNo = outTradeNo(orderId);
        bizJson.put("out_trade_no",outTradeNo);//我可以把这个订单号更新到订单表去
        bizJson.put("total_amount",10000);//单位是元
        bizJson.put("subject",map.get("setmeal"));//我可以查询套餐名称
        bizJson.put("product_code","QUICK_WAP_PAY");

        orderService.updatePayNo(outTradeNo,orderId);//更新outTradeNo到订单里面，可以在回调里面根据这个参数更新订单状态

        alipayRequest.setBizContent(bizJson.toJSONString());//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    /**
     * 生成商户订单号（年月日时分秒 + 订单号）
     * @param orderId
     * @return
     */
    private String outTradeNo(Integer orderId){
        String dateStr = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        return dateStr + orderId;
    }



    /**
     * 支付宝通知我们支付状态，在这个接口里面可以修改订单支付的状态
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/alipayNotify")
    @ResponseBody
    public String alipayNotify(HttpServletRequest request, HttpServletRequest response) throws Exception {

        log.info("支付成功, 进入异步通知接口...");

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, "utf-8", "RSA2"); //调用SDK验证签名
        //——请在这里编写您的程序（以下代码仅作参考）——
		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知

                // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
                orderService.updatePayStatus(out_trade_no);
            }
            log.info("支付成功...");
        }else {//验证失败
            log.info("支付, 验签失败...");
        }

        return "success";
    }}
