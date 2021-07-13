package com.xtn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xtn.common.AddressUtil;
import com.xtn.vo.InStockVO;
import com.xtn.vo.PaginationVo;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import com.xtn.common.IPUtil;
import com.xtn.domain.LoginLog;
import com.xtn.mapper.LoginLogMapper;
import com.xtn.service.LoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 登录日志表 服务实现类
 * </p>
 *
 * @author xcoder
 * @since 2021-04-04
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Resource
    private LoginLogMapper loginLogMapper;

    //记录登录日志
    @Override
    public void add(String username,HttpServletRequest request) {
        LoginLog loginLog = createLoginLog(username, request);
        loginLogMapper.insert(loginLog);
    }

    @Override
    public PaginationVo<LoginLog> findLoginLogList(Integer pageNum, Integer pageSize, LoginLog loginLog) {
        PaginationVo<LoginLog> vo = new PaginationVo<>();
        //pageNum:查询的页数，pageSize:一页显示的数量
        PageHelper.startPage(pageNum,pageSize);
        List<LoginLog> consumerList = loginLogMapper.selectLogList(loginLog);
        //获取总记录数pageInfo.getTotal()
        PageInfo<LoginLog> pageInfo = new PageInfo<>(consumerList);
        vo.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        vo.setDataList(consumerList);
        return vo;
    }

    /**
     * 创建登入日志
     * @param
     * @return
     */
    public static LoginLog createLoginLog(String username,HttpServletRequest request) {

        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setIp(IPUtil.getIpAddr(request));
        if (loginLog.getIp() == "127.0.0.1"){
            loginLog.setLocation("本地测试");
        }else {
            try {
                loginLog.setLocation(AddressUtil.getAddresses(IPUtil.getIpAddr(request)));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        // 获取客户端操作系统
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        OperatingSystem os = userAgent.getOperatingSystem();
        loginLog.setUserSystem(os.getName());
        loginLog.setUserBrowser(browser.getName());
        loginLog.setLoginTime(new Date());
        return loginLog;
    }
}
