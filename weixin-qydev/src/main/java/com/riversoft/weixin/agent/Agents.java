package com.riversoft.weixin.agent;

import com.riversoft.weixin.agent.bean.Agent;
import com.riversoft.weixin.agent.bean.AgentsList;
import com.riversoft.weixin.agent.bean.WritableAgent;
import com.riversoft.weixin.base.WxClient;
import com.riversoft.weixin.util.JsonMapper;
import com.riversoft.weixin.util.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by exizhai on 9/25/2015.
 */
public class Agents {

    private static Logger logger = LoggerFactory.getLogger(Agents.class);

    private static Agents agents = null;
    private WxClient wxClient;

    public static Agents defaultAgents() {
        if (agents == null) {
            agents = new Agents();
            agents.setWxClient(WxClient.defaultWxClient());
        }

        return agents;
    }

    public void setWxClient(WxClient wxClient) {
        this.wxClient = wxClient;
    }

    public List<Agent> list() {
        String url = PropertiesLoader.getInstance().getProperty("url.agent.list");
        String content = wxClient.get(url);

        logger.debug("list agents: {}", content);
        AgentsList agentsList = JsonMapper.nonEmptyMapper().fromJson(content, AgentsList.class);
        return agentsList.getAgents();
    }

    public Agent get(int id) {
        String url = PropertiesLoader.getInstance().getProperty("url.agent.get");

        String agent = wxClient.get(String.format(url, id));
        logger.debug("get agent: {}", agent);
        return JsonMapper.nonEmptyMapper().fromJson(agent, Agent.class);
    }

    public void update(Agent agent) {
        WritableAgent writableAgent = new WritableAgent();

        writableAgent.setAgentId(agent.getAgentId());
        writableAgent.setName(agent.getName());
        writableAgent.setDescription(agent.getDescription());
        writableAgent.setReportLocationFlag(agent.getReportLocationFlag());
        writableAgent.setReportUserChange(agent.isReportUserChange());
        writableAgent.setReportUserEnter(agent.isReportUserEnter());
        writableAgent.setLogoMediaId(agent.getLogoMediaId());

        String url = PropertiesLoader.getInstance().getProperty("url.agent.set");

        String json = JsonMapper.nonEmptyMapper().toJson(writableAgent);
        logger.info("update agent: {}", json);
        wxClient.post(url, json);
    }
}