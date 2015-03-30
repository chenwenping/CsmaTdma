public class Csma {

	/**
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int nodeCount;
		int totalTime;
		int timeSlice = 0;
		double transferLikelihood = 0.5; // the likelihood for a node to
											// transfer data when it gets key
		int delay = 0;
		int isTransferring = 0;
		int transferringnode = -1;// node which is transferring
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

		// wait time for each node
		int waitTime[] = new int[nodeCount];
		for (int i = 0; i < nodeCount; i++) {
			waitTime[i] = 0;
		}

		while (timeSlice < totalTime) {
			// iterate every node to see whether it have data to transfer
			for (int i = 0; i < nodeCount; i++) {
				if (hasDataToTransfer[i] == 0 && waitTime[i] == 0) {
					double a = Math.random();
					// System.out.println("----------a-----" +a);
					if (a < transferLikelihood) {
						hasDataToTransfer[i] = 1;
						// System.out.println("node " + i +
						// " generates new data");
					}
				}
			}

			// count of requesting node
			int requestCount = 0;
			for (int i = 0; i < nodeCount; i++) {
				if (waitTime[i] > 0) {
					waitTime[i]--;
				}
				// a requesting and not waiting node counts one
				if (hasDataToTransfer[i] == 1 && waitTime[i] == 0) {
					requestCount++;
				}
			}

			if (isTransferring == 1) {
				dataRemain--;
				if (dataRemain == 0) {
					// finish transferring
					isTransferring = 0;
					hasDataToTransfer[transferringnode] = 0;
				}
				if (requestCount > 1) {
					// count delay
					delay = delay + requestCount - 1;
				}
			} else {
				// if there is only one node requesting, then start
				// transmitting
				if (requestCount == 1) {
					isTransferring = 1;
					double data = Math.random() * (2 * dataPiece) + 1;
					dataSize += data;
					dataRemain = (int) data;
					for (int i = 0; i < nodeCount; i++) {
						if (hasDataToTransfer[i] == 1 && waitTime[i] == 0) {
							transferringnode = i;
						}
					}
					System.out.println("node " + transferringnode
							+ " transfer " + dataRemain);
				} else if (requestCount > 1) {
					// collision happened
					System.out.println("collision happended");
					for (int i = 0; i < nodeCount; i++) {
						// set wait time for them
						if (hasDataToTransfer[i] == 1 && waitTime[i] == 0) {
							double data = Math.random() * (2 * dataPiece) + 1;
							waitTime[i] = (int) data;
						}
					}
				}
			}
			timeSlice++;
		}

		float time = totalTime;
		float throughput = dataSize / time;

		System.out.println("TDMA's system delay is " + delay
				+ " and throughput is " + throughput);
	}
}
