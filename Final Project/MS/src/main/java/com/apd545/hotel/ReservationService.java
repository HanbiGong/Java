package com.apd545.hotel;

import java.time.LocalDate;
import java.util.List;

public class ReservationService {

    private static final ReservationService instance = new ReservationService();
    public static ReservationService getInstance() { return instance; }

    private final ReservationRepository repo = ReservationRepository.getInstance();
    private final ActivityLogService logService = ActivityLogService.getInstance();

    private ReservationService() {}

    public Reservation createReservation(String guest,
                                         String room,
                                         LocalDate in,
                                         LocalDate out,
                                         String addons,
                                         double total,
                                         String phone) {

        Reservation r = new Reservation(
                guest, room, in, out, addons, total, phone
        );

        repo.save(r);
        logService.log(phone, "RESERVATION CREATED: ID=" + r.getId());
        return r;
    }

    public List<Reservation> getAll() {
        return repo.findAll();
    }

    public Reservation findById(int id) {
        return repo.findById(id);
    }

    public Reservation findByPhone(String phone) {
        return repo.findByPhone(phone);
    }

    public void update(Reservation r) {
        repo.update(r);
        logService.log(r.getPhoneNumber(), "RESERVATION UPDATED: ID=" + r.getId());
    }

    public void cancel(Reservation r) {
        r.setStatus("Cancelled");
        repo.update(r);

        logService.log(r.getPhoneNumber(), "RESERVATION CANCELLED: ID=" + r.getId());
    }

    public void checkout(Reservation r, double discount, int points) {

        double newTotal = r.getTotal() - discount - points;
        if (newTotal < 0) newTotal = 0;

        r.setTotal(newTotal);
        r.setStatus("Checked-out");

        repo.update(r);

        logService.log(
                r.getPhoneNumber(),
                "CHECKOUT COMPLETE: ID=" + r.getId() +
                        " | discount=" + discount +
                        " | points=" + points +
                        " | finalTotal=" + newTotal
        );
    }

    /** -------------------------------
     *  SAVE FEEDBACK (with sentiment)
     *  ------------------------------- */
    public void saveFeedback(Reservation r, int rating, String comments) {

        r.setRating(rating);
        r.setComments(comments);

        // --- Sentiment rule ---
        String sentiment;
        if (rating >= 4) sentiment = "Positive";
        else if (rating == 3) sentiment = "Neutral";
        else sentiment = "Negative";

        r.setSentiment(sentiment);

        repo.update(r);

        logService.log(
                r.getPhoneNumber(),
                "FEEDBACK SUBMITTED: ID=" + r.getId() +
                        ", rating=" + rating +
                        ", sentiment=" + sentiment
        );
    }
}
