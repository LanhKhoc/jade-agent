/** ***************************************************************
 * JADE - Java Agent DEvelopment Framework is a framework to develop
 * multi-agent systems in compliance with the FIPA specifications.
 * Copyright (C) 2000 CSELT S.p.A.  *
 * GNU Lesser General Public License
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation,
 * version 2.1 of the License.  *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 **************************************************************** */
package helper;

import jade.util.leap.*;

import jade.proto.*;
import jade.lang.acl.*;

import jade.domain.JADEAgentManagement.*;
import jade.domain.mobility.MobilityOntology;
import jade.domain.FIPANames;

import jade.content.lang.Codec;

import jade.core.*;

import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import server.StoreServer;
import server.ui.container.ServerMonitorContainer;

/*
  * This behaviour extends SimpleAchieveREInitiator in order
  * to request to the AMS the list of available locations where
  * the agent can move.
  * Then, it displays these locations into the GUI
  * @author Fabio Bellifemine - CSELT S.p.A.
  * @version $Date: 2003-02-25 13:29:42 +0100 (mar, 25 feb 2003) $ $Revision: 3687 $
 */
public class GetAvailableLocationsBehaviour extends SimpleAchieveREInitiator {
    private ACLMessage request;

    public GetAvailableLocationsBehaviour(Agent agent) {
        super(agent, new ACLMessage(ACLMessage.REQUEST));
        request = (ACLMessage) getDataStore().get(REQUEST_KEY);

        // NOTE: Fills all parameters of the request ACLMessage
        request.clearAllReceiver();
        request.addReceiver(agent.getAMS());
        request.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
        request.setOntology(MobilityOntology.NAME);
        request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

        // NOTE: Creates the content of the ACLMessage
        try {
            Action action = new Action();
            action.setActor(agent.getAMS());
            action.setAction(new QueryPlatformLocationsAction());
            agent.getContentManager().fillContent(request, action);
        } catch (Codec.CodecException | OntologyException fe) {
            fe.printStackTrace();
        }

        reset(request);
    }

    @Override
    protected void handleNotUnderstood(ACLMessage reply) {
        System.out.println(myAgent.getLocalName() + " handleNotUnderstood : " + reply.toString());
    }

    @Override
    protected void handleRefuse(ACLMessage reply) {
        System.out.println(myAgent.getLocalName() + " handleRefuse : " + reply.toString());
    }

    @Override
    protected void handleFailure(ACLMessage reply) {
        System.out.println(myAgent.getLocalName() + " handleFailure : " + reply.toString());
    }

    @Override
    protected void handleAgree(ACLMessage reply) {
    }

    @Override
    protected void handleInform(ACLMessage inform) {
        try {
            StoreServer.listLocations.clear();
            Result results = (Result) myAgent.getContentManager().extractContent(inform);
            Iterator iterator = results.getItems().iterator();
            while (iterator.hasNext()) {
                Location location = (Location) iterator.next();
                StoreServer.listLocations.add(location);
            }

            ServerMonitorContainer.showListLocations();
        } catch (Codec.CodecException | OntologyException e) {
            e.printStackTrace();
        }
    }
}
