package com.cf.mall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.cf.mall.bean.UmsMember;
import com.cf.mall.bean.UmsMemberReceiveAddress;
import com.cf.mall.service.MemberService;
import com.cf.mall.user.mapper.UmsMemberMapper;
import com.cf.mall.user.mapper.UmsMemberReceiveAddressMapper;
import com.cf.mall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author chen
 * @Date 2019/12/31
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private UmsMemberMapper memberMapper;
    @Autowired
    private UmsMemberReceiveAddressMapper addressMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public int deleteByPrimaryKey(String id) {
        return memberMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UmsMember record) {
        return memberMapper.insert(record);
    }

    @Override
    public int insertSelective(UmsMember record) {
        return memberMapper.insertSelective(record);
    }

    @Override
    public UmsMember selectByPrimaryKey(String id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UmsMemberReceiveAddress> selectByMemberKey(String memberId) {
        Example e = new Example(UmsMemberReceiveAddress.class);
        e.createCriteria().andEqualTo("memberId",memberId);
        return addressMapper.selectByExample(e);
    }

    @Override
    public List<UmsMember> selectAll() {
        return memberMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKeySelective(UmsMember record) {
        return memberMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UmsMember record) {
        return memberMapper.updateByPrimaryKey(record);
    }

    @Override
    public UmsMember login(UmsMember member) {
        UmsMember loginMember = null;

        String key = "user:" + member.getUsername() + ":info";
        try (Jedis jedis = redisUtil.getJedis()) {
            String infoStr = jedis.get(key);
            if (StringUtils.isBlank(infoStr)) {
                loginMember = memberMapper.selectOne(member);
                if (null != loginMember) {
                    jedis.set(key, JSON.toJSONString(loginMember));
                }
            } else {
                loginMember = JSON.parseObject(infoStr,UmsMember.class);
                if (!loginMember.getPassword().equals(member.getPassword())) {
                    loginMember = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginMember;
    }

    @Override
    public void setMemberToken(Long id, String token) {
        String key = "user:" + id + ":token";
        try (Jedis jedis = redisUtil.getJedis()) {
            jedis.setex(key,60*60*2,token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertSocial(UmsMember um) {
        Example e = new Example(UmsMember.class);
        e.createCriteria().andEqualTo("sourceUid",um.getSourceUid());
        List<UmsMember> umsMemberList = memberMapper.selectByExample(e);
        if (umsMemberList.size() > 0) {
            memberMapper.insertSelective(um);
        } else {
            memberMapper.updateByPrimaryKey(um);
        }
    }
}
