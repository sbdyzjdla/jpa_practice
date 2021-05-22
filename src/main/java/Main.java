import domain.Member;
import domain.Team;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            //logic(em);
            testSave(em);
            //queryLogicJoin(em);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

//    public static void logic(EntityManager em) {
//
//        String id = "id1";
//        Member member = new Member();
//        member.setId(id);
//        member.setUsername("지한");
//        member.setAge(2);
//        //등록
//        em.persist(member);
//
//        //수정
//        member.setAge(20);
//
//        //한 건 조회
//        Member findMember = em.find(Member.class, id);
//        System.out.println("find domain.Member Id : " + findMember.getId()
//        + ", member name " + findMember.getUsername()
//        + ", member age : " + findMember.getAge());
//
//        //목록 조회
//        List<Member> members = em.createQuery("select m from domain.Member m", Member.class)
//                .getResultList();
//        System.out.println("Members = " + members);
//        System.out.println("domain.Member.size = " + members.size());
//
//        em.remove(member);
//    }

    //연관관계 매핑(저장)
    public static void testSave(EntityManager em) {

        //팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        //회원1 저장
        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1);
        em.persist(member1);

        //회원2 저장
        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1);
        em.persist(member2);

        biDreiction(em);
    }

    //JPQL
    public static void queryLogicJoin(EntityManager em) {

        String jpql = "select m from Member m join m.team t where t.name=:teamName";

        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "팀1")
                .getResultList();

        for(Member member : resultList) {
            System.out.println("[query] member.username = " + member.getUsername());
        }
    }

    //수정
    private static void updateRelation(EntityManager em) {

        //새로운 팀2
        Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        //회원1에 새로운 팀2 설정
        Member member = em.find(Member.class, "member1");
        member.setTeam(team2);
    }

    //연관관계 삭제
    private static void deleteRelation(EntityManager em) {

        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(null);
    }

    //연관된 엔티티 삭제
    // 연관된 엔티티를 삭제하려면 외래키 제약조건에 의하여 오류 발생
    // 연관관계 먼저 제거후 삭제
    // member1.setTeam(null);
    // em.remove(team);

    //team 조회 양방향
    public static void biDreiction(EntityManager em) {
        Team team = em.find(Team.class, "team1");
        List<Member> members = team.getMembers();
        System.out.println("!!!!!!!!!!!"+ team.getName());

        Member member1 = em.find(Member.class, "member1");
        System.out.println("@@@@@@@"+ member1.getUsername());

        for(Member member : members) {
            System.out.println("member.username =" + member.getUsername());
        }
    }
}
