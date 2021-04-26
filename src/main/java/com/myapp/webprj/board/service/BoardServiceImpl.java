package com.myapp.webprj.board.service;

import com.myapp.webprj.board.domain.Board;
import com.myapp.webprj.board.mapper.BoardMapper;
import com.myapp.webprj.common.Criteria;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
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
    public boolean modify(Board board) {
        return boardMapper.update(board) == 1;
    }

    @Override
    public boolean remove(Long bno) {
        return boardMapper.delete(bno) == 1;
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
