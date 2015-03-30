public class Tdma {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int nodeCount;
		int totalTime;
		int timeSlice = 0;
		double transferLikelihood = 0.5; // the likelihood for a node to
											// transfer data when it gets key
		int key = 0; // key is in n's node's hand
		int delay = 0;
		int isTransferring = 0;
		int dataPiece = 10; // average data size to transfer
		int dataSize = 0; // total transferred data
		int dataRemain = 0;

		java.util.Scanner scanner = new java.util.Scanner(System.in);
		System.out.println("please input the numbers of node: ");
		nodeCount = scanner.nextInt();
		System.out.println("pleas input the total time: ");
		totalTime = scanner.nextInt();

		// whether a node has date to transfer
		int hasDataToTransfer[] = new int[nodeCount];
		for (int i = 0; i < nodeCount; i++) {
			hasDataToTransfer[i] = 0;
		}

		while (timeSlice < totalTime) {
			// iterate every node to see whether it have data to transfer
			for (int i = 0; i < nodeCount; i++) {
				// if a node already has data, then pass this one
				if (hasDataToTransfer[i] == 0) {
					double a = Math.random();
					// System.out.println("----------a-----" +a);
					if (a < transferLikelihood) {
						hasDataToTransfer[i] = 1;
						// System.out.println("node " + i
						// + " generates new data");
					}
				}
			}

			// if the none-key node has data to transfer, count to one time
			// slice of delay
			for (int i = 0; i < nodeCount; i++) {
				if (i != key) {
					if (hasDataToTransfer[i] == 1) {
						delay++;
					}
				}
			}

			// if the key holder node has data to transfer
			if (hasDataToTransfer[key] == 1) {
				// if is not transferring, then start transfer
				if (isTransferring == 0) {
					isTransferring = 1;
					// assign a random data size
					double data = Math.random() * (2 * dataPiece) + 1;
					dataSize += data;
					dataRemain = (int) data;
					// System.out.println("----------data-----" +data);
					System.out.println("node " + key + " transfer "
							+ dataRemain + " data");
				} else {
					// if is transferring
					if (dataRemain == 0) {
						isTransferring = 0;
						hasDataToTransfer[key] = 0;
						key = (key + 1) % nodeCount;
					}
				}
				dataRemain--;
			} else {
				// if the key holder node doesn't have data to transfer,then
				// pass key to next node
				key = (key + 1) % nodeCount;
			}
			timeSlice++;
		}

		float time = totalTime;
		float throughput = dataSize / time;

		System.out.println("TDMA's system delay is " + delay
				+ " and throughput is " + throughput);
	}
}
