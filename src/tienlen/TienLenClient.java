package tienlen;

import card.Card;
import card.Deck;
import game.Client;

public class TienLenClient extends Client {

	public TienLenClient(int n)
	{
		super(n,false);
	}
	
	@Override
    public void start(Deck deck) {
		
		this.deck = deck;
		
		for (int i = 0; i <numOfPlayers; i++)
		{
			playerList.get(i).removeAllCards();
		}
		
		for (int i = 0; i < numOfPlayers; i++)
		{
			for (int j = 0; j < 13; j++)
			{
				getPlayerList().get(i).addCard(this.deck.getCard(i*13+j));
			}
		}
		
		for (int i = 0; i < numOfPlayers; i++)
		{
			getPlayerList().get(i).getCardsInHand().sort();
		}
		
		minCard = null;
 		
 		for (int i = 0; i < numOfPlayers; i++)
 		{
 			for(int j = 0; j < playerList.get(i).getCardsInHand().size(); j++) {
 				Card card = playerList.get(i).getCardsInHand().getCard(j);
 				if(minCard == null) {
 					setMinCard(card);
 					currentIdx = i;
 				} 
 				else if(card.compareTo(getMinCard()) == -1) {
 					currentIdx = i;
 					setMinCard(card);
 				}
 			}
 		}		
		table.repaint();
		table.setActivePlayer(playerID);
		if(isBot) {
 			if(playerID == currentIdx) {
 				makeMoveAutomatically();
 			}
		}
	}
	
}
