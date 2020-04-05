package com.matrix.arithmetic.search;

/**
 * 单手翻转烙饼问题，详见编程之美
 * 基本思想：遍历搜索树+有界剪枝
 * 
 * @author rayeaster
 *
 */
public class CakeTuneProblem {
    
	int[] m_cakeArray; // 烙饼信息数组
	int m_nCakeCnt;    // 烙饼个数
	int m_nMaxSwap;    // 最多交换次数,最多为2*(m_nCakeCnt-1)
	int[] m_swapArray; // 交换结果数组
	int[] m_reverseCakeArray; // 当前翻转烙饼信息数组
	int[] m_reverseCakeArraySwap; // 当前翻转烙饼交换信息数组
	int m_nSearch; //当前搜索次数信息
    
    public CakeTuneProblem(){
        m_nCakeCnt = 0;
        m_nMaxSwap = 0;
    }
    
    void run(int[] pCakeArray){
        init(pCakeArray);
        m_nSearch = 0;
        search(0);
    }

    void output(){
        for(int i = 0; i < m_nMaxSwap; i++)
            System.out.print(m_swapArray[i]+",");
        
        System.out.println(" Search Times:"+m_nSearch);
        System.out.println("Total Swap Times:"+m_nMaxSwap);
    }
    
    void init(int[] pCakeArray){
        assert( pCakeArray != null);
        assert( pCakeArray.length > 0);
        
        m_nCakeCnt = pCakeArray.length;
        
        m_cakeArray = new int[m_nCakeCnt];
        
        for(int i = 0; i < m_nCakeCnt; i++)
            m_cakeArray[i] = pCakeArray[i];
        
        m_nMaxSwap = upperBound(m_nCakeCnt);
        
        m_swapArray = new int[m_nMaxSwap];
        
        m_reverseCakeArray = new int[m_nCakeCnt];
        for(int i = 0; i < m_nCakeCnt; i++)
            m_reverseCakeArray[i] = m_cakeArray[i];
        
        m_reverseCakeArraySwap = new int[m_nMaxSwap];
    }
    
    
    int lowerBound(int[] pCakeArray)
    {        
        int t, ret = 0;
    
        for(int i = 1; i < pCakeArray.length; i++){
            t = pCakeArray[i] - pCakeArray[i-1];
            if( (t==1) || (t == -1)){
            }
            else{
                ret ++;
            }        
        }
        return ret;        
    }
    
    
    int upperBound(int nCakeCnt){
        //return (nCakeCnt-1)*2;
        return (nCakeCnt)*2;
    }
    
    
    void search(int step){
        int i, nEstimate;
        m_nSearch++;
        
        //prune condition
        nEstimate = lowerBound(m_reverseCakeArray);
        if( step + nEstimate > m_nMaxSwap || step >= m_nMaxSwap)
            return;
        
        if( isSorted( m_reverseCakeArray)){
            if( step < m_nMaxSwap){
                m_nMaxSwap = step;
                for(i = 0; i < m_nMaxSwap; i++)
                    m_swapArray[i] = m_reverseCakeArraySwap[i];
            }
            return;
        }
        
        for(i = 1; i < m_nCakeCnt; i++){
            revert(0,i);
            m_reverseCakeArraySwap[step] = i;
            search(step+1);
            revert(0,i);
        }
        
    }
    
    boolean isSorted(int[] pCakeArray)
    {
        for(int i = 1; i < pCakeArray.length; i++)        
            if( pCakeArray[i-1] > pCakeArray[i])
                return false;
        return true;    
    }
    
    void revert(int nBegin, int nEnd)
    {
        assert( nEnd > nBegin);
        int t = 0;
        for( int i=nBegin, j=nEnd; i<j; i++,j--)
        {
            t = m_reverseCakeArray[i];
            m_reverseCakeArray[i] = m_reverseCakeArray[j];
            m_reverseCakeArray[j] = t;
        }
    }
    
    
    public static void main(String[] args) {
        CakeTuneProblem problem = new CakeTuneProblem();
        //int[] cakeArray = {3,2,1,6,5,4,9,8,7,0}; 
        int[] cakeArray = {3,2,1,6,5,4,9,8,7,0};
        problem.run(cakeArray);
        problem.output();
    }

}
