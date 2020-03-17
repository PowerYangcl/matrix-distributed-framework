package com.matrix.sxt.e08;

public enum CountryEnmu {
    One(1, "齐" , "姜小白"),
    Two(2 ,"楚" ,"楚庄王"),
    Three(3, "燕","燕昭王"),
    Four(4, "赵","赵武灵王"),
    Five(5, "魏","魏武卒"),
    Six(6, "韩","韩非子");

    public static CountryEnmu foreach(int index){
        CountryEnmu[] arr = CountryEnmu.values();
        for (CountryEnmu ele : arr){
            if(index == ele.getId()){
                return ele;
            }
        }
        return null;
    }

    private Integer id;
    private String  name;
    private String remark;

    CountryEnmu(Integer id, String name, String remark) {
        this.id = id;
        this.name = name;
        this.remark = remark;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
