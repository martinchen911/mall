package com.cf.mall.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.cf.mall.bean.UmsMember;
import com.cf.mall.bean.UmsMemberReceiveAddress;
import com.cf.mall.service.MemberService;
import com.cf.mall.user.mapper.UmsMemberMapper;
import com.cf.mall.user.mapper.UmsMemberReceiveAddressMapper;
import com.cf.mall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author chen
 * @Date 2019/12/31
 */
@RequestMapping("member")
@RestController
public class MemberServiceImpl implements MemberService {

    @Autowired
    private UmsMemberMapper memberMapper;
    @Autowired
    private UmsMemberReceiveAddressMapper addressMapper;
    @Autowired
    private RedisUtil redisUtil;

    @DeleteMapping("deleteByPrimaryKey")
    @Override
    public int deleteByPrimaryKey(@RequestParam String id) {
        return memberMapper.deleteByPrimaryKey(id);
    }

    @PostMapping("insert")
    @Override
    public int insert(@RequestBody UmsMember record) {
        return memberMapper.insert(record);
    }

    @PostMapping("insertSelective")
    @Override
    public int insertSelective(@RequestBody UmsMember record) {
        return memberMapper.insertSelective(record);
    }

    @PostMapping("selectByPrimaryKey")
    @Override
    public UmsMember selectByPrimaryKey(@RequestParam String id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @PostMapping("selectByMemberKey")
    @Override
    public List<UmsMemberReceiveAddress> selectByMemberKey(@RequestParam String memberId) {
        Example e = new Example(UmsMemberReceiveAddress.class);
        e.createCriteria().andEqualTo("memberId",memberId);
        return addressMapper.selectByExample(e);
    }

    @GetMapping("selectAll")
    @Override
    public List<UmsMember> selectAll() {
        return memberMapper.selectAll();
    }

    @PutMapping("updateByPrimaryKeySelective")
    @Override
    public int updateByPrimaryKeySelective(@RequestBody UmsMember record) {
        return memberMapper.updateByPrimaryKeySelective(record);
    }

    @PutMapping("updateByPrimaryKey")
    @Override
    public int updateByPrimaryKey(@RequestBody UmsMember record) {
        return memberMapper.updateByPrimaryKey(record);
    }

    @PostMapping("login")
    @Override
    public UmsMember login(@RequestBody UmsMember member) {
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

    @PutMapping("setMemberToken")
    @Override
    public void setMemberToken(@RequestParam Long id,@RequestParam String token) {
        String key = "user:" + id + ":token";
        try (Jedis jedis = redisUtil.getJedis()) {
            jedis.setex(key,60*60*2,token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("insertSocial")
    @Override
    public void insertSocial(@RequestBody UmsMember um) {
        Example e = new Example(UmsMember.class);
        e.createCriteria().andEqualTo("sourceUid",um.getSourceUid());
        List<UmsMember> umsMemberList = memberMapper.selectByExample(e);
        if (umsMemberList.size() > 0) {
            memberMapper.insertSelective(um);
        } else {
            memberMapper.updateByPrimaryKey(um);
        }
    }

    @PostMapping("getAddress")
    @Override
    public UmsMemberReceiveAddress getAddress(@RequestParam String addressId) {
        UmsMemberReceiveAddress address = new UmsMemberReceiveAddress();
        address.setId(Long.parseLong(addressId));
        return addressMapper.selectOne(address);
    }
}
