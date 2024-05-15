package com.cgm.hello_web_app.restful;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cgm.hello_web_app.dao.DonHangDAO;
import com.cgm.hello_web_app.eitities.DonHang;

@Path("/DonHangs")
public class DonHangServiceAPI {
    private DonHangDAO donHangDao = new DonHangDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDonHangs() {
        List<DonHang> donHangs = donHangDao.getLatestDonHangList();
        return Response.ok(donHangs).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDonHang(DonHang donHang) throws URISyntaxException {
        if (donHang == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid DonHang data").build();
        }

        boolean success = donHangDao.addDonHang(donHang);
        if (success) {
            // Thêm thành công, trả về status 201 và URI của đơn hàng mới
            return Response.created(new URI("/DonHangs/" + donHang.getMaDonHang())).build();
        } else {
            // Nếu thất bại, trả về status 500
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add DonHang").build();
        }
    }
}
