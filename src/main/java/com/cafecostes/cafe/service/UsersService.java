package com.cafecostes.cafe.service;

public class UsersService {
//    private UsersRepository userRepository;
//
//    @Transactional
//    public Long save(UsersSaveRequestDto dto){
//        return userRepository.save(dto.toEntity()).getUserNum();
//    }

    // 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도를 개선함
    // 그렇기에 특별히 등록, 수정, 삭제 기능이 없는 메소드에서 사용하는걸 추천함
//    @Transactional(readOnly = true)
//    public List<UsersMainResponseDto> findAllDesc() {
//        return userRepository.findAllDesc()
//                .map(UsersMainResponseDto::new)
//                .collect(Collectors.toList());
//    }
}
