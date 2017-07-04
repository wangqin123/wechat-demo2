package com.wq.wechat.service;

import com.wq.wechat.bean.Page;

/**
 * Created by Mifeng.He(bee) on 2015/11/17.
 * 二维码生成
 * 用户扫描带场景值二维码时，可能推送以下两种事件：
 * 如果用户已经关注公众号，在用户扫描后会自动进入会话，微信也会将带场景值扫描事件推送给开发者。
 * 获取带参数的二维码的过程包括两步，首先创建二维码ticket，然后凭借ticket到指定URL换取二维码。
 */
public interface QRCodeService {

    /**
     * 临时二维码url, ticket生成（七天）
     * @param sceneId 场景值ID，临时二维码时为32位非0整型
     * @return
     */
    Page<Object> temporary(String sceneId);

    /**
     * 临时二维码url, ticket生成
     * @param sceneId 场景值ID，临时二维码时为32位非0整型
     * @param expireSeconds 二维码有效时间，以秒为单位。 最大不超过604800（即7天）。
     * @return
     */
    Page<Object> temporary(String sceneId, Integer expireSeconds);

    /**
     * 永久二维码url, ticket生成
     * @param sceneId 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     * @return
     */
    Page<Object> permanent(Integer sceneId);

    /**
     * 永久二维码url, ticket生成
     * @param sceneStr 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64，仅永久二维码支持此字段
     * @return
     */
    Page<Object> permanent(String sceneStr);

    /**
     * 生成二维码图片
     * @param ticket
     * @return
     */
    Page<Object> qrcode(String ticket);

    /**
     * 临时二维码url, ticket生成（七天）图片
     * @param sceneId 场景值ID，临时二维码时为32位非0整型
     * @return
     */
    Page<Object> temporaryQrcode(String sceneId);

    /**
     * 临时二维码url, ticket生成图片
     * @param sceneId 场景值ID，临时二维码时为32位非0整型
     * @param expireSeconds 二维码有效时间，以秒为单位。 最大不超过604800（即7天）。
     * @return
     */
    Page<Object> temporaryQrcode(String sceneId, Integer expireSeconds);

    /**
     * 永久二维码url, ticket生成图片
     * @param sceneId 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     * @return
     */
    Page<Object> permanentQrcode(Integer sceneId);

    /**
     * 永久二维码url, ticket生成图片
     * @param sceneStr 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64，仅永久二维码支持此字段
     * @return
     */
    Page<Object> permanentQrcode(String sceneStr);

}
