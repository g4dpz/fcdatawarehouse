package com.badgersoft.datawarehouse.rawdata.service;

import com.badgersoft.datawarehouse.rawdata.shared.Ranking;

public interface UserRankingService {
    Ranking getRanking(int draw, int sort, int start, int length, String search);
}
