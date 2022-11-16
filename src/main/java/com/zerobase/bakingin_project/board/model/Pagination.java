package com.zerobase.bakingin_project.board.model;

import lombok.Data;

@Data
public class Pagination {
    /**
     *  페이지 당 보여지는 게시글 최대 갯수
     */
    private int pageSize = 10;
    /**
     *  페이징된 버튼의 블럭당 최대 개수
     */
    private int blockSize = 5;
    /**
     *  현재 페이지
     */
    private int page = 1;
    /**
     *  현재 블럭
     */
    private int block = 1;
    /**
     *  총 게시글 수
     */
    private int totalListCnt;
    /**
     *  총 페이지 수
     */
    private int totalPageCnt;
    /**
     *  총 블럭 수
     */
    private int totalBlockCnt;
    /**
     *  블럭 시작 페이지
     */
    private int startPage = 1;
    /**
     *  블럭 마지막 페이지
     */
    private int endPage = 1;
    /**
     *  이전 블럭 마지막 페이지
     */
    private int prevBlock;
    /**
     *  다음 블럭 시작 페이지
     */
    private int nextBlock;

    /**
     *  DB 접근 시작 index
     */
    private int startIndex = 0;

    /**
     *  이전 페이지 여부
     */
    private boolean prev;
    /**
     *  다음 페이지 여부
     */
    private boolean next;
    public Pagination(int totalListCnt, int page) {

        // 총 게시물 수와 현재 페이지를 BoardController에서 받아온다.

        /** 현재 페이지 **/
        setPage(page);


        /** 총 게시글 수 **/
        setTotalListCnt(totalListCnt);

        /** 페이지 수 **/
        // 한 페이지의 최대 개수를 총 게시물 수 * 1.0로 나누어주고 올림
        setTotalPageCnt((int) Math.ceil(totalListCnt * 1.0 / pageSize));


        /** 총 블럭 수 **/
        // 한 블럭의 최대 개수를 총  페이지의 수 * 1.0로 나누어주고 올림
        setTotalBlockCnt((int) Math.ceil(totalPageCnt * 1.0 / blockSize));


        /** 현재 블럭 **/
        // 현재 페이지 * 1.0을 블록의 최대 개수로 나누어주고 올림한다.
        setBlock((int) Math.ceil((page * 1.0)/blockSize));


        /** 블럭 시작 페이지 **/
        setStartPage((block - 1) * blockSize + 1);


        /** 블럭 마지막 페이지 **/
        setEndPage(startPage + blockSize - 1);


        /* === 블럭 마지막 페이지에 대한 validation ===*/
        if(endPage > totalPageCnt){
            this.endPage = totalPageCnt;
        }


        /** 이전 블럭(클릭 시, 이전 블럭 마지막 페이지) **/
        setPrevBlock((block * blockSize) - blockSize);


        /* === 이전 블럭에 대한 validation === */
        if(prevBlock < 1) {this.prevBlock = 1;}


        /** 다음 블럭(클릭 시, 다음 블럭 첫번째 페이지) **/
        setNextBlock((block * blockSize) + 1);


        /* === 다음 블럭에 대한 validation ===*/
        if(nextBlock > totalPageCnt) {nextBlock = totalPageCnt;}

        this.next = endPage == totalPageCnt ? false : true;
        this.prev = startPage == 1 ? false : true;


        /**  DB 접근 시작 index **/
        setStartIndex((page-1) * pageSize);
    }
}