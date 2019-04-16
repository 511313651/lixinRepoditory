package src.main.java.store;

import java.util.*;

/*排列与组合
本类处理的问题是关于排列与组合的
根据给定的元素集合,找出这些元素的所有排列与组合的方式.
 */
public class PermutationAndCombination{
    public static void main(String[] args) {
        Character[] chars = {'a','b','c'};
        ArrayList<ArrayList<Character>> permutation = permutation(chars);
        System.out.println(permutation);
    }

    //输入一个字符数组,以字符串数组的方式返回这些字符数组的所有组合方式
    /*
    1.思路:
        字符数组中的每一个字符,都只有两种可能性,要么被选择,要么没被选择,可以用1和0表示
        所以,一个长度为n的字符数组,总共有2^n种组合方式(包含了空字符串)
        可以用长度为n的所有二进制整数表示其中的每一种组合.
     2.相关知识:
        Integer.toBinaryString(int i)可以将十进制整数转换为二进制字符串.
     */
    public static <T> ArrayList<ArrayList<T>> combination(T[] chars) {
        ArrayList<ArrayList<T>> arrayLists = new ArrayList<>();
        int n = (int) Math.pow(2, chars.length);
        for (int i = 0; i < n; i++) {
            arrayLists.add(choose(chars, i));
        }
        return arrayLists;
    }

    //传入一个备选的数组和一个表示选择的数字,返回选择到的数组.
    private static <T> ArrayList<T> choose(T[] chars, int i){
        //判断输入是否合法,0<=i<(2^chars.length)-1
        int length = chars.length;
        if(i<0 || i>Math.pow(2,length))
            throw new RuntimeException("参数不合法!");
        ArrayList<T> characters = new ArrayList<>();
        String flagList = Integer.toBinaryString(i);
        for (int flagIndex = flagList.length()-1,charsIndex=0; flagIndex >=0; flagIndex--,charsIndex++) {
            if (flagList.charAt(flagIndex)=='1') {
                characters.add(chars[charsIndex]);
            }
        }
        return characters;
    }

    //传入一个ArrayList<T>集合和一个元素T,返回该元素插入该集合各个位置的所有组合

    private static <T> ArrayList<ArrayList<T>> insertToOne(ArrayList<T> arrayList, T element){
        ArrayList<ArrayList<T>> arrayLists = new ArrayList<>();
        for (int i = 0; i <= arrayList.size(); i++) {
            ArrayList<T> arrayListColne = ((ArrayList<T>) arrayList.clone());
            arrayListColne.add(i,element);
            arrayLists.add(arrayListColne);
        }
        return arrayLists;
    }
    //传入一个ArrayList<T>集合的集合ArrayList<ArrayList<T>>和一个元素T,

    // 返回该元素插入该集合中的每个元素集合中的所有位置的所有组合
    private static <T> ArrayList<ArrayList<T>> insertToMany(ArrayList<ArrayList<T>> arrayList, T element){
        ArrayList<ArrayList<T>> arrayLists = new ArrayList<>();
        if(arrayList.size()==0){
            ArrayList<T> ts = new ArrayList<T>();
            ts.add(element);
            arrayLists.add(ts);
            return arrayLists;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            ArrayList<ArrayList<T>> subArrayLists = insertToOne(arrayList.get(i), element);
            arrayLists.addAll(subArrayLists);
        }
        return arrayLists;
    }
    //如果集合长度是0,则返回的集合中包含一个集合,该集合只包含element一个元素.

    //传入一个ArrayList集合original,返回该ArrayList中所有元素的排列形式
    /*
    思路:
        先从original中取出第一个元素,添加到集合的集合ArrayList<ArrayList<T>> arrayLists中,arrayLists长度是1.
        再从original中取出第二个元素,使用insertToMany方法,将元素添加到arrayLists中,arrayLists长度是1*2;
        ...arrayLists长度是1*2*...*n;
        重复上面的步骤,直到添加完最后一个元素.arrayLists长度是1*2*...*original.size();这个可能性的种数也正好是全排列数.
    */
    public static <T> ArrayList<ArrayList<T>> permutationAll(ArrayList<T> original){
        ArrayList<ArrayList<T>> arrayLists = new ArrayList<>();
        for (int i = 0; i < original.size(); i++) {
            T t = original.get(i);
            ArrayList<ArrayList<T>> buffArrayLists = insertToMany(arrayLists, t);
            arrayLists.clear();
            arrayLists.addAll(buffArrayLists);
        }
        return arrayLists;
    }

    //传入一个数组,求该数组中所有元素的不完全排列数
    public static <T> ArrayList<ArrayList<T>> permutation(T[] arr){
        ArrayList<ArrayList<T>> arrayLists = new ArrayList<ArrayList<T>>();
        ArrayList<ArrayList<T>> combination = combination(arr);
        for (int i = 0; i < combination.size(); i++) {
            ArrayList<T> combinationI = combination.get(i);
            ArrayList<ArrayList<T>> permutationAll_combinationI = permutationAll(combinationI);
            arrayLists.addAll(permutationAll_combinationI);
        }

        Comparator<ArrayList<T>> ListComparator = new Comparator<ArrayList<T>>() {
            @Override
            public int compare(ArrayList<T> list1, ArrayList<T> list2) {

                if(list1.size()!=list2.size())return list1.size()-list2.size();
                if(!(list1.get(0) instanceof Comparable))return 1;//不是很严谨,待优化
                for (int i = 0; i < list1.size(); i++) {
                    int compare = ((Comparable) list1.get(i)).compareTo(list2.get(i));
                    if(compare!=0)return compare;
                }
                return 0;
            }

            @Override
            public boolean equals(Object obj) {
                return Objects.deepEquals(this,obj);
            }
        };
        Collections.sort(arrayLists,ListComparator);
        return arrayLists;
    }

    /*
        思路,先求出所有的组合数,再把每一种组合数进行全排列
    */

}
