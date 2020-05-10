/**
 * 	public class ChineseWordFrequence 
 */
package beans;

import java.io.*;
import java.util.*;
import java.util.Map;
import java.util.Map.Entry;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.recognition.impl.StopRecognition;
import org.ansj.splitWord.analysis.*;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;

/**
 * 对一个目录下的中文文本进行抽取，并统计词频,按降序对词频进行排序，关键使用tika的API对中文文本的抽取
 * ansj是ictclas(中科院分词)的java实现.
 * 目前实现了: 1.中文分词 2.中文姓名识别 3.用户自定义词典
 * //未定义词典分词
 * System.out.println(ToAnalysis.parse(text));
 * 类Term为DAG的节点，字段包括：offe首字符在句子中的位置、name为词，next具有相同首字符的节点、from前驱节点、score打分。
 * @author 王宇峰
 *
 */
public class AnsjSplitAndWordCount {
		public String[] Ansj_SplitAndWordCount(String IR_Original_String) throws Throwable{
			//String resultPath = "G:/Workspaces/MyEclipse 2017 CI/AnsjSplitAndWordCount/wordFreText_NlpAnalysis.txt";//结果文件的路径
			Map<String, Integer> wordFreCount = getWordFrequence(IR_Original_String);
			//saveResult(wordFreCount,resultPath);
			String[] keyArrays_entire = wordFreCount.keySet().toArray(new String[wordFreCount.size()]);  //把所有的key保存到数组里面
			String[] keyArrays = new String[6];
			if(keyArrays_entire.length > 6){  //提取排名前6的关键词
				for(int i =0; i<6;i++){
					keyArrays[i] = keyArrays_entire[i];
				}
				return keyArrays;
			}
			else return keyArrays_entire;
			
		}
		
		
		private static Map<String, Integer> getWordFrequence(String IR_Original_String) throws Throwable{
			//System.out.println("开始提取目录下的文本");
			Map<String, Integer> wordFrequence = new HashMap<>();    //创建一个string-int的键值对
			String text = IR_Original_String;  //将读取到的文件 转化成字符串！			
			//只关注这些词性的词，使用集合的数据结构（这里只统计名词）
	        Set<String> expectedNature = new HashSet<String>() {{
	            add("n");add("nr");add("nr1");add("nr2");add("nrj");add("nrt"); add("nrf");
	            add("ns");add("nsf");
	            add("nt");add("nz");add("nw");add("nl");add("ng");
	            add("wh");
	            add("nth");add("nto");add("nts");add("ntu");add("nis");add("nhm");add("nnd");add("nit");add("nnt");
	            add("ntc");add("ntcb");add("ntcf");add("ntch");add("nto");
	            add("gi");add("gm");add("gp");
	           
	        }};
	        StopRecognition filter = new StopRecognition(); //实例化一个过滤器
	        //filter.insertStopWords("科"); //过滤单词	        
			//Result result = NlpAnalysis.parse(text).recognition(filter);  //
	        //最核心部分，进行分词并过滤！ 分词结果的一个封装，主要是一个List<Term>的terms
//	        File file = new File("G:/Workspaces/MyEclipse 2017 CI/SecondHandShopping/library/userLibrary.dic");
//	        if (!file.exists()) {
//	            throw new RuntimeException("要读取的文件不存在");
//	        }
//	      //创建文件字节读取流对象时，必须明确与之关联的数据源
//	        FileInputStream fis = new FileInputStream(file);
//	      //读取 - 将输入流的数据传递给字节数组
//	        //根据流中的字节长度，创建字节数组，保存读到的数据
//	        byte[] contentByte = new byte[fis.available()];
//	        //将字节流中的数据传递给字节数组
//	        fis.read(contentByte);
//	        //将字节数组转为字符串
//	        String userLibrary = new String(contentByte);
//	        Forest forest= Library.makeForest(userLibrary);   //拟合自定义模型
	        Forest forest= Library.makeForest(AnsjSplitAndWordCount.class.getResourceAsStream("/library/userLibrary.dic"));//加载字典文件
	    
			Result result = ToAnalysis.parse(text,forest);
			List<Term> terms = result.getTerms();   ////拿到terms

			for(int i=0; i<terms.size(); i++) {
	            String word = terms.get(i).getName(); //拿到词
	            String natureStr = terms.get(i).getNatureStr(); //拿到词性
	            
	            if(expectedNature.contains(natureStr)) {  //如果是规定的词性
	                System.out.print(word + ":" + natureStr+"  ");
	            	if(!wordFrequence.containsKey(word)){  //如果map中不存在这个词，则加入，词频设为1
						wordFrequence.put(word, 1);
					}else{                                 //存在，词频直接+1
						wordFrequence.put(word, wordFrequence.get(word) + 1);
					}
	            	
	            }
	        }
			System.out.println(" ");
			System.out.println("排序前wordFrequence : "+wordFrequence);/*----------------------------*/
			Map<String,Integer >wordFrequence_Descend = sortDescend(wordFrequence);//按值(词频)进行排序		
			System.out.println("排序后wordFrequence : "+wordFrequence_Descend);/*----------------------------*/
			return wordFrequence_Descend;	
				
			}		
		

		/*按value值的大小进行排序
		 * Comparator()定义一个比较器的方法，它可以用于指定比较list里面对象的各个属性值
		 */
		// Map的value值降序排序
	    public static <K, V extends Comparable<? super V>> Map<K, V> sortDescend(Map<K, V> map) {
	        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
	        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
	            @Override
	            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
	                int compare = (o1.getValue()).compareTo(o2.getValue());
	                return -compare;
	            }
	        });
	 
	        Map<K, V> returnMap = new LinkedHashMap<K, V>();  //构造排序好的map
	        for (Map.Entry<K, V> entry : list) {
	            returnMap.put(entry.getKey(), entry.getValue());
	        }
	        return returnMap;
	    }



//		//保存结果
//		private static void saveResult(Map<String, Integer> wordFreCount,String resultPath) throws FileNotFoundException{
//			File file = new File(resultPath);
//			PrintWriter pw = new PrintWriter(resultPath);
//			for(String str:wordFreCount.keySet()){  //遍历map
//				pw.write(str + "\t" + wordFreCount.get(str) + "\n");
//			}
//			pw.close();
//		}




		public static void main(String[] args) throws Throwable {
//			//String filePath = "G:/Workspaces/MyEclipse 2017 CI/AnsjSplitAndWordCount/第二章.txt";//读取的中文文本目录
		//String resultPath = "G:/Workspaces/MyEclipse 2017 CI/AnsjSplitAndWordCount/wordFreText_NlpAnalysis.txt";//结果文件的路径
			Map<String, Integer> wordFreCount = getWordFrequence("人工智能大数据边缘计算硬件边缘计算数据库技术");
			//saveResult(wordFreCount,resultPath);

			String[] keyArrays_entire = wordFreCount.keySet().toArray(new String[wordFreCount.size()]);  //把所有的key保存到数组里面
			String[] keyArrays = new String[6];
			if(keyArrays_entire.length > 6){  //提取排名前6的关键词
				for(int i =0; i<6;i++){
				keyArrays[i] = keyArrays_entire[i];
				}
			}
			else keyArrays = keyArrays_entire;
			System.out.println("keyArrays: "+Arrays.toString(keyArrays));
			
		}

}

