package com.example.board.dto;

import com.example.board.domain.Category;
import com.example.board.domain.CurrencyType;
import com.example.board.domain.ServerType;
import com.example.board.domain.TradeStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PostForm {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 200, message = "제목은 200자 이내로 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @NotNull(message = "서버를 선택해주세요.")
    private ServerType serverType = ServerType.LADDER;

    @NotNull(message = "카테고리를 선택해주세요.")
    private Category category = Category.ETC;

    @NotBlank(message = "아이템 이름을 입력해주세요.")
    @Size(max = 200, message = "아이템 이름은 200자 이내로 입력해주세요.")
    private String itemName;

    @NotNull(message = "화폐 단위를 선택해주세요.")
    private CurrencyType currencyType = CurrencyType.RUNE;

    @Size(max = 50, message = "상세 화폐명은 50자 이내로 입력해주세요.")
    private String currencyName;

    @NotNull(message = "가격을 입력해주세요.")
    private Integer priceAmount = 1;

    private TradeStatus tradeStatus = TradeStatus.SELLING;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public ServerType getServerType() { return serverType; }
    public void setServerType(ServerType serverType) { this.serverType = serverType; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public CurrencyType getCurrencyType() { return currencyType; }
    public void setCurrencyType(CurrencyType currencyType) { this.currencyType = currencyType; }

    public String getCurrencyName() { return currencyName; }
    public void setCurrencyName(String currencyName) { this.currencyName = currencyName; }

    public Integer getPriceAmount() { return priceAmount; }
    public void setPriceAmount(Integer priceAmount) { this.priceAmount = priceAmount; }

    public TradeStatus getTradeStatus() { return tradeStatus; }
    public void setTradeStatus(TradeStatus tradeStatus) { this.tradeStatus = tradeStatus; }
}
