package cc.blisscorp.event.game.dev;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import cc.blisscorp.event.game.ent.LuckyBoxEvent;
import cc.blisscorp.event.game.ent.Event.Status;
import cc.blisscorp.event.game.ent.EventFilter;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.jdbc.manager.BaseManager;
import cc.blisscorp.event.game.jdbc.manager.LuckyBoxEventManager;

public class Serialization {
	
	public static void main(String[] args) throws Exception {
		
		int[] options = new int[]{3,4,5,6,7,8,9,10};
		double[] weights = new double[]{ 0.85/3d, 0.85/3d, 0.85/3d,
		                                 0.10/3d, 0.10/3d, 0.10/3d,
		                                 0.05/2d, 0.05/2d };

		NavigableMap<Double, Integer> map = new TreeMap<Double, Integer>();
		double totalWeight = 0d;

		for (int i = 0; i < weights.length; i++) {
		    totalWeight += weights[i];
		    map.put(totalWeight, options[i]);
		}

		
	//  select from the weighted elements
		Random rand = new Random();
		HashMap<Integer, Double> freqs = new HashMap<Integer, Double>();
		int iterations = 10000;
		for(int i = 0; i < iterations; i++) {
		    double rnd = rand.nextDouble() * totalWeight;
		    int elem = map.ceilingEntry(rnd).getValue();
		    freqs.put(elem, (freqs.containsKey(elem) ? freqs.get(elem) : 0) + (1d/iterations));
		}
		Map<Integer, Double> sortedFreqs = new TreeMap<Integer, Double>(freqs);

		for(Map.Entry<Integer,Double> entry : sortedFreqs.entrySet()) {
		    System.out.printf("%02d: %.2f%% %n", entry.getKey(), entry.getValue() * 100d);
		}

		
//		Transaction tran = new Transaction();
//		tran.setGameId(2);
//		tran.setAmount(10000);
//		tran.setUserId(102);
//		tran.setStatus(Transaction.Status.PENDING);
//		tran.setCreatedAt(new Date());
//		TransManager.getInstance().saveOrUpdate(tran);
		
//		User user = new User();
//		user.setUserId(101);
//		user.setScore(30);
//		user.setGiftTotal(10);
//		user.setCreatedAt(new Date());
//		UserManager.getInstance().saveOrUpdate(user);
		
//		Gifts gifts = new Gifts();
//		gifts.setType(Gifts.Type.CARD);
//		gifts.setStatus(Gifts.Status.NEW);
//		gifts.setAmount(100000);
//		gifts.setPercentage(0.5f);
//		gifts.setPercentageTotal(100f);
//		gifts.setReceivedDate(new Date());
//		gifts.setUserId(102);
//		gifts.setEventId(1);
////		
//		GiftsManager.getInstance().saveOrUpdate(gifts);
//		
//		searchEvent();
		
//		updateScoreUser(user.getUserId(), 1023);
	}
	
	public static int getNumber() {
		Random rand = new Random();
		int r = rand.nextInt(10000000);
		int m = r % 10;
		if (m < 1) return 1; // 10 %
		else if (m < 4) return 2; // 30 %
		else return 3; // 60 %
	}
	
	public static void searchEvent() {
//		Event event;
		EventFilter filter = new EventFilter();
		filter.setStatus(Status.ACTIVE);
		BaseManager<LuckyBoxEvent, EventFilter> eventMgr = LuckyBoxEventManager.getInstance();
		try {
			PageView<LuckyBoxEvent> pv = eventMgr.search(filter);
			for (LuckyBoxEvent event : pv.getItems()) {
				System.out.println(event.getId());
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

}
