package com.myapp.webprj.board.service;

import com.myapp.webprj.board.domain.Board;
import com.myapp.webprj.board.mapper.BoardMapper;
import com.myapp.webprj.common.Criteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;


    @Transactional
    @Override
    public void register(Board board) {
        boardMapper.save(board);

        //첨부파일이 있는 경우
        List<String> fileNames = board.getFileNames();
        if (fileNames != null) {
            for (String fileName : fileNames) {
                boardMapper.addFile(fileName);
            }
        }
    }

    @Override
    public Board get(Long bno) {
        return boardMapper.findByBno(bno);
    }

    @Override
    public List<String> getFileNames(Long bno) {
        return boardMapper.findFileNames(bno);
    }

    @Override
    public boolean modify(Board board) {
        return boardMapper.update(board) == 1;
    }

    @Transactional
    @Override
    public boolean remove(Long bno) {
        log.info("remove service start!");
        try {
            return boardMapper.delete(bno) == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Board> getList(Criteria cri) {
        return boardMapper.getListWithPaging(cri);
    }

    @Override
    public int getTotal(Criteria cri) {
        return boardMapper.getSearchTotalCount(cri);
    }

    @Override
    public List<Board> searchList(Criteria cri) {
        List<Board> searchList = boardMapper.getSearchList(cri);

        return searchList;
    }
}
